package com.example.userjwtauthwebservice.user.dto;

import com.example.userjwtauthwebservice.user.entities.User;

public interface UserDetailMapper {

    static UserDetail from (User entity){
        if (entity == null)
            return null;
        return new UserDetail(
                entity.getId(),
                entity.getUsername(),
                entity.isAdministrator()
        );
    }
}
