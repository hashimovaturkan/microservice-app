package com.microservice.authservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserView {

    private Integer id;

    private String username;
    private String fullName;
    private Set<RoleDto> roles = new HashSet<>();
    private String password;

}
