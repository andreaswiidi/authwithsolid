package com.solid.auth.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "session")
public class Session extends BaseModel{
    @Id
    @SequenceGenerator(
            name = "session_pk_seq",
            sequenceName = "session_pk_seq",
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "session_pk_seq"
    )
    private Long id;
    private Long userId;
    private String username;
    private Date expSession;
}
