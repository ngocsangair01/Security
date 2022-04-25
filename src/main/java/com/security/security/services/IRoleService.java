package com.security.security.services;

import com.security.security.daos.Role;
import com.security.security.dtos.RoleDTO;

import java.util.Set;

public interface IRoleService {
    Role createRole(RoleDTO roleDTO);
    Set<Role> getAllRole();
    Role editRole(Integer idRole,RoleDTO roleDTO);
    Role deleteRole(Integer idRole);
}
