package sit.int221.oasipservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipservice.dtos.CreateUserDTO;
import sit.int221.oasipservice.dtos.UpdateUserDTO;
import sit.int221.oasipservice.entities.User;
import sit.int221.oasipservice.services.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<User> getUsers(Authentication auth) {
        System.out.println(auth.getName());
        auth.getAuthorities().forEach(System.out::println);
        return service.getUsers();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody CreateUserDTO newUser) {
        return service.create(newUser);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public User update(@Valid @RequestBody UpdateUserDTO updateUser, @PathVariable Integer id) {
        return service.update(updateUser, id);
    }
}
