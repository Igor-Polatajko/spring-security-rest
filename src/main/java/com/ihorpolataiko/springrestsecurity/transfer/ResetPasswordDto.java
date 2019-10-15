package com.ihorpolataiko.springrestsecurity.transfer;

import com.ihorpolataiko.springrestsecurity.transfer.validation.Messages;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordDto {
    @NotNull(message = Messages.REQUIRED_NOT_EMPTY)
    private String oldPassword;
    @NotNull(message = Messages.REQUIRED_NOT_EMPTY)
    @Size(min = 6, message = "Password must be at least 6 symbols long")
    @Pattern(regexp = ".*[A-Za-z]+.*", message = "Pattern must contain at least 1 alphabetical character")
    @Pattern(regexp = ".*[0-9]+.*", message = "Pattern must contain at least 1 numeric character")
    private String newPassword;
}
