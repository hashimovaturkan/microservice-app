package com.microservice.authservice.domain.dto;

import lombok.Data;

@Data
public class RoleToUserFrom {
    private String username;
    private String roleName;
}
