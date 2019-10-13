package com.ihorpolataiko.springrestsecurity.domain;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "user_id")
    private String id;
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
    private boolean active;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Token> tokens;
}
