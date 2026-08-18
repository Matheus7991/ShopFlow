package com.shopflow.auth.infrastructure.persistence.mapper;

import com.shopflow.auth.domain.model.User;
import com.shopflow.auth.infrastructure.persistence.entity.UserEntity;

public class UserMapper {

    private UserMapper() {

    }

    public static User toDomain(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserEntity toEntity(User user){
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
