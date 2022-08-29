package sit.int221.oasipservice.dtos;


import lombok.Getter;
import lombok.Setter;
import sit.int221.oasipservice.utils.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class UpdateUserDTO {
    @NotBlank(message =  "Name is empty")
    @Size(max = 100, message = "Name is more than 100 char")
    private String name;

    @NotBlank(message =  "Email is empty")
    @Email(message = "Email is not correctly")
    @Size(max = 50, message = "Email is more than 50 char")
    private String email;

    private Role role;

}