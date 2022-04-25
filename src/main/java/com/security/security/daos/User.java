package com.security.security.daos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Boolean status = Boolean.TRUE;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "id_user",referencedColumnName = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_role",referencedColumnName = "id_role"))
//    @JsonIgnore
    private Set<Role> roles;
}
