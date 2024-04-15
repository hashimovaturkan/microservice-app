package com.microservice.userservice.domain.mapper;

import com.microservice.common.UserRequest;
import com.microservice.userservice.domain.dto.CreateUserRequest;
import com.microservice.userservice.domain.dto.UpdateUserRequest;
import com.microservice.userservice.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public User create(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullname());
        user.setPassword(request.getPassword());
        // Authorities will be set in afterCreate method
        return user;
    }

    public void update(UpdateUserRequest request, User user) {
        user.setFullName(request.getFullName());
        // Authorities will be set in afterUpdate method
    }

}
