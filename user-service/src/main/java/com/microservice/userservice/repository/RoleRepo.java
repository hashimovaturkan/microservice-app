package com.microservice.userservice.repository;

import com.microservice.userservice.domain.exception.NotFoundException;
import com.microservice.userservice.domain.model.Role;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "roles")
public interface RoleRepo extends MongoRepository<Role, ObjectId> {

    @CacheEvict(allEntries = true)
    <S extends Role> List<S> saveAll(Iterable<S> entities);

    @Caching(evict = {
            @CacheEvict(key = "#p0.id", condition="#p0.id != null")
    })
    <S extends Role> S save(S entity);

    @Cacheable
    Optional<Role> findById(ObjectId objectId);

    @Cacheable
    default Role getById(ObjectId id) {
        Optional<Role> optionalRole = findById(id);
        if (optionalRole.isEmpty()) {
            throw new NotFoundException(Role.class, id);
        }
        return optionalRole.get();
    }

    @Cacheable
    Optional<Role> findByAuthority(String authority);



}
