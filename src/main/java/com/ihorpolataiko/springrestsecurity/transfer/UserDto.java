package com.ihorpolataiko.springrestsecurity.transfer;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.transfer.validation.ExistingRecord;
import com.ihorpolataiko.springrestsecurity.transfer.validation.NewRecord;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_EMPTY;
import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_NOT_EMPTY;

@Data
@Builder
public class UserDto {
    @Null(groups = {NewRecord.class}, message = REQUIRED_EMPTY)
    @NotNull(groups = {ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
    private String id;
    private String username;
    @NotNull(groups = {NewRecord.class}, message = REQUIRED_NOT_EMPTY)
    @Null(groups = {ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
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
