package com.security.security.utils;

import com.security.security.daos.Role;
import com.security.security.daos.User;
import com.security.security.dtos.RoleDTO;
import com.security.security.dtos.UserDTO;

public class Convert {
    public static User fromUserDTOToUser(UserDTO userDTO, User user){
        if (userDTO.getName() != null){
            user.setName(userDTO.getName());
        }
        if (userDTO.getUsername() != null){
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null){
            user.setPassword(userDTO.getPassword());
        }
        return user;
    }

    public static Role fromRoleDTOToRole(RoleDTO roleDTO,Role role){
        if (roleDTO.getName() != null){
            role.setName(roleDTO.getName());
        }
        return role;
    }
}
