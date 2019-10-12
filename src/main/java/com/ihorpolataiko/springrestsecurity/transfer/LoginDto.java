package com.ihorpolataiko.springrestsecurity.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_NOT_EMPTY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull(message = REQUIRED_NOT_EMPTY)
    private String username;
    @NotNull(message = REQUIRED_NOT_EMPTY)
    private String password;
}
