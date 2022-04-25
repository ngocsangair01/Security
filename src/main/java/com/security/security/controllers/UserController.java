package com.security.security.controllers;

import com.security.security.dtos.UserDTO;
import com.security.security.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(200).body(userService.createUser(userDTO));
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.status(200).body(userService.getAllUser());
    }

    @GetMapping("/{idRole}")
    public ResponseEntity<?> getAllUserByRole(@PathVariable(name = "idRole")Integer idRole){
        return ResponseEntity.status(200).body(userService.getAllUserByRole(idRole));
    }
}
