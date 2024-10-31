package com.solid.auth.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users extends BaseModel {
    @Id
    @SequenceGenerator(
            name = "users_pk_seq",
            sequenceName = "users_pk_seq",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_pk_seq"
    )
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean isDeleted;
    private Boolean isEmailVerified ;
}
