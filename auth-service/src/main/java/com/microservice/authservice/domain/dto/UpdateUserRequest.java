package com.microservice.authservice.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UpdateUserRequest {

    @NotBlank
    private String userName;
    private Set<String> authorities;

}
