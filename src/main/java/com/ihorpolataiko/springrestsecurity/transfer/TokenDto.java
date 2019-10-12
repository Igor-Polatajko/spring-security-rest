package com.ihorpolataiko.springrestsecurity.transfer;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    private String value;

    public static TokenDto from(Token token) {
        return new TokenDto(token.getValue());
    }
}
