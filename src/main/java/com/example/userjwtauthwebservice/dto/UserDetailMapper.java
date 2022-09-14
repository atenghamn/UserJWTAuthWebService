package com.example.userjwtauthwebservice.dto;

import com.example.userjwtauthwebservice.entities.User;

public interface UserDetailMapper {

    static UserDetail from (User entity){
        if (entity == null)
            return null;
        return new UserDetail(
                entity.getId(),
                entity.getUsername()
        );
    }
}
