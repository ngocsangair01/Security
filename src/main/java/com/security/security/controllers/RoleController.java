package com.security.security.controllers;

import com.security.security.dtos.RoleDTO;
import com.security.security.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.status(200).body(roleService.createRole(roleDTO));
    }

    @GetMapping
    public ResponseEntity<?> getAllRole(){
        return ResponseEntity.status(200).body(roleService.getAllRole());
    }


}
