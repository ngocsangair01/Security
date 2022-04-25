package com.security.security;

import com.security.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
public class SecurityApplication {


    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
