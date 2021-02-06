package com.ihorpolataiko.springrestsecurity.domain.security;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.transfer.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "token_id")
    private String id;

    private String value;

    private LocalDateTime lastActivityTime;

    private LocalDateTime createdTime;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    public TokenDto toDto() {
        return TokenDto.builder()
                .value(value)
                .build();
    }

}
