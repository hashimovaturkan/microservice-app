package com.microservice.authservice.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper {

    private final ObjectIdMapper objectIdMapper;

    @Autowired
    public UserEditMapper(ObjectIdMapper objectIdMapper) {
        this.objectIdMapper = objectIdMapper;
    }

}
