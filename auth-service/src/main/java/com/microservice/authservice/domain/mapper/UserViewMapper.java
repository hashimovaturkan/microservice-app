package com.microservice.authservice.domain.mapper;

import com.microservice.authservice.domain.dto.UserView;
import com.microservice.common.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
//public abstract class UserViewMapper {
//    public abstract UserView toUserView(User user);
//    public abstract List<UserView> toUserView(List<User> users);
//
//
//}
@Component
public class UserViewMapper {

    private final ObjectIdMapper objectIdMapper;

    @Autowired
    public UserViewMapper(ObjectIdMapper objectIdMapper) {
        this.objectIdMapper = objectIdMapper;
    }

    public UserView toUserView(UserResponse user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setUsername(user.getUsername());
        userView.setFullName(user.getFullname());
        return userView;
    }

    public List<UserView> toUserView(List<UserResponse> users) {
        return users.stream()
                .map(this::toUserView)
                .collect(Collectors.toList());
    }
}