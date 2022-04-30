package com.security.security.controllers;

import com.security.security.daos.Role;
import com.security.security.daos.User;
import com.security.security.dtos.UserDTO;
import com.security.security.exceptions.BadRequestException;
import com.security.security.filters.AuthenticationResponse;
import com.security.security.payload.AuthenticationRequest;
import com.security.security.repositories.RoleRepository;
import com.security.security.repositories.UserRepository;
import com.security.security.services.IRoleService;
import com.security.security.services.IUserService;
import com.security.security.services.imp.MyUserDetailsService;
import com.security.security.utils.Convert;
import com.security.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()
            ));
        }catch (BadCredentialsException e) {
            throw new BadRequestException("Incorrect username or password");
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(authenticationRequest.getUsername());
        List<String> roles = new ArrayList<>();
        Set<Role> roleSet = user.getRoles();
        if(roleSet.size() > 0) {
            roleSet.forEach(item -> roles.add(item.getName()));
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getIdUser().longValue(), user.getUsername(), roles));
    }

    @PostMapping("/signup-user")
    public ResponseEntity<?> signupUser(@RequestBody UserDTO userDTO) throws InvalidObjectException {
        User oldUser = userRepository.findByUsername(userDTO.getUsername());
        if(oldUser != null) {
            throw new BadRequestException("Username has already exists");
        }
        User user = new User();
        Convert.fromUserDTOToUser(userDTO,user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //gán role member cho user mới lập
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(role));

        User newUser = userRepository.save(user);

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getIdUser().longValue(), newUser.getUsername(), List.of(role.getName())));
    }
    @PostMapping("/signup-admin")
    public ResponseEntity<?> signupAdmin(@RequestBody UserDTO userDTO) throws InvalidObjectException {
        User oldUser = userRepository.findByUsername(userDTO.getUsername());
        if(oldUser != null) {
            throw new BadRequestException("Username has already exists");
        }
        User user = new User();
        Convert.fromUserDTOToUser(userDTO,user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //gán role member cho user mới lập
        Role role = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(Set.of(role));

        User newUser = userRepository.save(user);

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(newUser.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getIdUser().longValue(), newUser.getUsername(), List.of(role.getName())));
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody AuthenticationResponse authenticationResponse) throws InvalidObjectException {
        try {
            String jwt = authenticationResponse.getJwt();
            String username = jwtUtil.extractUsername(jwt);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            User user = userRepository.findByUsername(username);

            Set<Role> roles = user.getRoles();

            return ResponseEntity.ok(new AuthenticationResponse(
                    jwtUtil.generateToken(userDetails),
                    user.getIdUser().longValue(),
                    user.getUsername(),
                    roles.stream().map(Role::getName).collect(Collectors.toList()))
            );
        } catch (Exception e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }


}
