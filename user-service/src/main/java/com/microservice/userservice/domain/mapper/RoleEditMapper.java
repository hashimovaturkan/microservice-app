package com.microservice.userservice.domain.mapper;

import com.microservice.userservice.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RoleEditMapper {
    private final ObjectIdMapper objectIdMapper;

    @Autowired
    public RoleEditMapper(ObjectIdMapper objectIdMapper) {
        this.objectIdMapper = objectIdMapper;
    }


    public Role create(com.microservice.common.Role request) {
        Role role = new Role();
        role.setAuthority(request.getRoleName());
        return role;
    }
}
