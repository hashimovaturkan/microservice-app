package com.microservice.authservice.domain.mapper;

import com.microservice.authservice.domain.dto.UserView;
import com.microservice.authservice.domain.model.User;
import com.microservice.authservice.repository.UserRepo;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public abstract class UserViewMapper {
    public abstract UserView toUserView(User user);

    public abstract List<UserView> toUserView(List<User> users);


}
