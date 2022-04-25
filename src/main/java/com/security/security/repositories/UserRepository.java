package com.security.security.repositories;

import com.security.security.daos.Role;
import com.security.security.daos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
    Set<User> findAllByRoles(Role role);

}
