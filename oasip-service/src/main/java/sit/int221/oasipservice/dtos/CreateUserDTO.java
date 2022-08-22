package sit.int221.oasipservice.dtos;

import lombok.Getter;
import lombok.Setter;
import sit.int221.oasipservice.utils.Role;

import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter @Setter
public class CreateUserDTO {

    @NotBlank(message =  "Name is empty")
    @Size(max = 100, message = "Name is more than 100 char")
    private String name;

    @NotBlank(message =  "Email is empty")
    @Email(message = "Email is not correctly")
    @Size(max = 50, message = "Email is more than 50 char")
    private String email;

    @NotNull(message = "role is empty")
    private Role role;

}