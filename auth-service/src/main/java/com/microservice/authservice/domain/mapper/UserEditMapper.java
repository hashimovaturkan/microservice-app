package com.microservice.authservice.domain.mapper;

import com.microservice.authservice.domain.dto.CreateUserRequest;
import com.microservice.authservice.domain.dto.UpdateUserRequest;
import com.microservice.authservice.domain.model.User;
import com.microservice.authservice.domain.model.Role;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

//@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
//public abstract class UserEditMapper {
//
//    @Mapping(target = "authorities", ignore = true)
//    public abstract User create(CreateUserRequest request);
//
//    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
//    @Mapping(target = "authorities", ignore = true)
//    public abstract void update(UpdateUserRequest request, @MappingTarget User user);
//
//    @AfterMapping
//    protected void afterCreate(CreateUserRequest request, @MappingTarget User user) {
//        if (request.getAuthorities() != null) {
//            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));
//        }
//    }
//
//    @AfterMapping
//    protected void afterUpdate(UpdateUserRequest request, @MappingTarget User user) {
//        if (request.getAuthorities() != null) {
//            user.setAuthorities(request.getAuthorities().stream().map(Role::new).collect(toSet()));
//        }
//    }
//
//}
@Component
public class UserEditMapper {

    private final ObjectIdMapper objectIdMapper;

    @Autowired
    public UserEditMapper(ObjectIdMapper objectIdMapper) {
        this.objectIdMapper = objectIdMapper;
    }

    public User create(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        // Authorities will be set in afterCreate method
        return user;
    }

    public void update(UpdateUserRequest request, User user) {
        user.setFullName(request.getFullName());
        // Authorities will be set in afterUpdate method
    }

    public void afterCreate(CreateUserRequest request, User user) {
        if (request.getAuthorities() != null) {
            Set<Role> roles = request.getAuthorities().stream()
                    .map(Role::new)
                    .collect(Collectors.toSet());
            user.setAuthorities(roles);
        }
    }

    public void afterUpdate(UpdateUserRequest request, User user) {
        if (request.getAuthorities() != null) {
            Set<Role> roles = request.getAuthorities().stream()
                    .map(Role::new)
                    .collect(Collectors.toSet());
            user.setAuthorities(roles);
        }
    }
}
