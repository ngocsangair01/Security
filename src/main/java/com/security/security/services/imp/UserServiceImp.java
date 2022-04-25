package com.security.security.services.imp;

import com.security.security.daos.Role;
import com.security.security.daos.User;
import com.security.security.dtos.UserDTO;
import com.security.security.exceptions.BadRequestException;
import com.security.security.exceptions.NotFoundException;
import com.security.security.repositories.RoleRepository;
import com.security.security.repositories.UserRepository;
import com.security.security.services.IUserService;
import com.security.security.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername());
        if (user != null){
            throw new BadRequestException("Duplicate user");
        }
        User user1 = new User();
        Convert.fromUserDTOToUser(userDTO,user1);
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        Set<Role> userRole = new HashSet<>();
        for (Role role : roles) {
            if (role.getName().compareTo("USER")==0){
                userRole.add(role);
            }
        }
        user1.setRoles(userRole);
        return userRepository.save(user1);
    }

    @Override
    public Set<User> getAllUser() {
        Set<User> users = new HashSet<>(userRepository.findAll());
        return users;
    }

    @Override
    public User editUser(Integer idUser,UserDTO userDTO) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isEmpty()){
            throw new NotFoundException("No user");
        }
        return userRepository.save(Convert.fromUserDTOToUser(userDTO,user.get()));
    }

    @Override
    public User deleteUser(Integer idUser) {
        Optional<User> user = userRepository.findById(idUser);
        if (user.isEmpty()){
            throw new NotFoundException("No User");
        }
        userRepository.delete(user.get());
        return user.get();
    }

    @Override
    public Set<User> getAllUserByRole(Integer idRole) {
        Optional<Role> role = roleRepository.findById(idRole);
        if (role.isEmpty()) {
            throw new NotFoundException("No role");
        }
        Set<User> users = userRepository.findAllByRoles(role.get());
        if (users.isEmpty()){
            throw new NotFoundException("No User");
        }
        return users;
    }
}
