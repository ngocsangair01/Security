package com.security.security.services.imp;

import com.security.security.daos.Role;
import com.security.security.dtos.RoleDTO;
import com.security.security.exceptions.BadRequestException;
import com.security.security.exceptions.NotFoundException;
import com.security.security.repositories.RoleRepository;
import com.security.security.services.IRoleService;
import com.security.security.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImp implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role role = roleRepository.findByName(roleDTO.getName());
        if (role != null) {
            throw new BadRequestException("Duplicate Role");
        }
        Role role1 = new Role();
        Convert.fromRoleDTOToRole(roleDTO,role1);
        return roleRepository.save(role1);
    }

    @Override
    public Set<Role> getAllRole() {
        Set<Role> roles = new HashSet<>(roleRepository.findAll());
        return roles;
    }

    @Override
    public Role editRole(Integer idRole, RoleDTO roleDTO) {
        Optional<Role> role = roleRepository.findById(idRole);
        if (role.isEmpty()){
            throw new NotFoundException("No role");
        }
        return roleRepository.save(Convert.fromRoleDTOToRole(roleDTO,role.get()));
    }

    @Override
    public Role deleteRole(Integer idRole) {
        Optional<Role> role = roleRepository.findById(idRole);
        if (role.isEmpty()){
            throw new NotFoundException("No role");
        }
        roleRepository.delete(role.get());
        return role.get();
    }
}
