package com.microservice.authservice.domain.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
//@Mapper(componentModel = "spring")
//public class ObjectIdMapper {
//
//    public String objectIdToString(ObjectId objectId) {
//        return objectId.toString();
//    }
//
//    public ObjectId stringToObjectId(String string) {
//        return new ObjectId(string);
//    }
//
//}

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdMapper {

    public String objectIdToString(ObjectId objectId) {
        return objectId.toString();
    }

    public ObjectId stringToObjectId(String string) {
        return new ObjectId(string);
    }
}
