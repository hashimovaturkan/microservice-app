package com.microservice.authservice.service;


import com.microservice.authservice.domain.dto.CreateUserRequest;
import com.microservice.authservice.domain.dto.RoleDto;
import com.microservice.authservice.domain.dto.UserDto;
import com.microservice.authservice.domain.dto.UserView;

import java.util.List;

public interface UserService {
    UserView create(CreateUserRequest userDto);
    RoleDto saveRole(RoleDto roleDto);
    void addRoleToUser(String username, String roleName);
    UserView getUser(String username);
    List<UserDto> getUsers();
}
