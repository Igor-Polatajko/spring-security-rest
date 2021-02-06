package com.ihorpolataiko.springrestsecurity.domain;

import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
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

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<Role> roles;

    private boolean active;

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .roles(roles)
                .active(active)
                .build();
    }

}
