package com.security.security.services;

import com.security.security.daos.User;
import com.security.security.dtos.UserDTO;

import java.util.Set;

public interface IUserService {
    User createUser(UserDTO userDTO);
    Set<User> getAllUser();
    User editUser(Integer idUser,UserDTO userDTO);
    User deleteUser(Integer idUser);
    Set<User> getAllUserByRole(Integer idRole);
}
