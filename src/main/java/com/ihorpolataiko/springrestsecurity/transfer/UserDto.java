package com.ihorpolataiko.springrestsecurity.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.transfer.validation.ExistingRecord;
import com.ihorpolataiko.springrestsecurity.transfer.validation.NewRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

import static com.ihorpolataiko.springrestsecurity.transfer.validation.Messages.REQUIRED_NOT_EMPTY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String username;

    @NotNull(groups = {NewRecord.class}, message = REQUIRED_NOT_EMPTY)
    @Size(groups = {NewRecord.class}, min = 6, message = "Password must be at least 6 symbols long")
    @Pattern(groups = {NewRecord.class}, regexp = ".*[A-Za-z]+.*",
            message = "Pattern must contain at least 1 alphabetical character")
    @Pattern(groups = {NewRecord.class}, regexp = ".*[0-9]+.*",
            message = "Pattern must contain at least 1 numeric character")
    @Null(groups = {ExistingRecord.class}, message = "You cannot update password via this endpoint")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
    private String firstName;

    @NotNull(groups = {NewRecord.class, ExistingRecord.class}, message = REQUIRED_NOT_EMPTY)
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Role> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean active;

}
