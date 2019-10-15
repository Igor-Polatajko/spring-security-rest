package com.ihorpolataiko.springrestsecurity.transfer;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.transfer.validation.ExistingRecord;
import com.ihorpolataiko.springrestsecurity.transfer.validation.NewRecord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_EMPTY;
import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_NOT_EMPTY;

@Data
@Builder
public class UserDto {
    private String id;
    private String username;
    @NotNull(groups = {NewRecord.class}, message = REQUIRED_NOT_EMPTY)
    @Size(groups = {NewRecord.class}, min = 6, message = "Password must be at least 6 symbols long")
    @Pattern(groups = {NewRecord.class}, regexp = ".*[A-Za-z]+.*",
            message = "Pattern must contain at least 1 alphabetical character")
    @Pattern(groups = {NewRecord.class}, regexp = ".*[0-9]+.*",
            message = "Pattern must contain at least 1 numeric character")
    @Null(groups = {ExistingRecord.class}, message = REQUIRED_EMPTY)
    private String password;
    @NotNull(groups = {NewRecord.class, ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
    private String firstName;
    @NotNull(groups = {NewRecord.class, ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
    private String lastName;
    @Null(groups = {NewRecord.class, ExistingRecord.class}, message = REQUIRED_EMPTY)
    private List<Role> roles;
    private boolean active;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .active(user.isActive())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .passwordHash(userDto.getPassword())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .active(userDto.isActive())
                .build();
    }
}
