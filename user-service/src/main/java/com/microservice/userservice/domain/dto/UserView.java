package com.microservice.userservice.domain.dto;

import lombok.Data;

@Data
public class UserView {

    private String id;

    private String username;
    private String fullName;

}
