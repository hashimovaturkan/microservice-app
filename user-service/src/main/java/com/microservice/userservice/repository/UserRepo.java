package com.microservice.userservice.repository;

import com.microservice.userservice.domain.dto.Page;
import com.microservice.userservice.domain.dto.SearchUsersQuery;
import com.microservice.userservice.domain.exception.NotFoundException;
import com.microservice.userservice.domain.model.Role;
import com.microservice.userservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
@CacheConfig(cacheNames = "users")
public interface UserRepo extends UserRepoCustom, MongoRepository<User, ObjectId> {

    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);

//    Bu yapı, save metodu çağrıldığında belirtilen koşullara bağlı olarak iki farklı anahtara sahip verilerin önbellekten temizlenmesini sağlar. Bu, özellikle verilerin güncel kalması gereken durumlarda kullanışlıdır.
    @Caching(evict = {
            @CacheEvict(key = "#p0.id", condition="#p0.id != null"),
            @CacheEvict(key = "#p0.username", condition="#p0.username != null")
    })
    <S extends User> S save(S entity);

    @Cacheable
    Optional<User> findById(ObjectId objectId);

    @Cacheable
    default User getById(ObjectId id) {
        Optional<User> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(User.class, id);
        }
        if (!optionalUser.get().isEnabled()) {
            throw new NotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

    @Cacheable
    Optional<User> findByUsername(String username);

}

interface UserRepoCustom {

    List<User> searchUsers(Page page, SearchUsersQuery query);
    void saveRoleToUser(String username, String roleName);


}

@RequiredArgsConstructor
class UserRepoCustomImpl implements UserRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        List<AggregationOperation> operations = new ArrayList<>();

        List<Criteria> criteriaList = new ArrayList<>();
        if (!StringUtils.isEmpty(query.getId())) {
            criteriaList.add(Criteria.where("id").is(new ObjectId(query.getId())));
        }
        if (!StringUtils.isEmpty(query.getUsername())) {
            criteriaList.add(Criteria.where("username").regex(query.getUsername(), "i"));
        }
        if (!StringUtils.isEmpty(query.getFullName())) {
            criteriaList.add(Criteria.where("fullName").regex(query.getFullName(), "i"));
        }
        if (!criteriaList.isEmpty()) {
            Criteria userCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
            operations.add(match(userCriteria));
        }

        operations.add(sort(Sort.Direction.DESC, "createdAt"));
        operations.add(skip((page.getNumber() - 1) * page.getLimit()));
        operations.add(limit(page.getLimit()));

        TypedAggregation<User> aggregation = newAggregation(User.class, operations);
        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, User.class);
        return results.getMappedResults();
    }

    @Override
    public void saveRoleToUser(String username, String roleName) {

        Query queryForUser = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(queryForUser, User.class);

        Query queryForRole = new Query(Criteria.where("authority").is(roleName));
        Role role = mongoTemplate.findOne(queryForRole, Role.class);

        if (user != null) {
            if (!user.getAuthorities().contains(role)) {
                user.getAuthorities().add(role);
                mongoTemplate.save(user);
            }
        }

    }
}
