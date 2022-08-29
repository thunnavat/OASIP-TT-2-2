package sit.int221.oasipservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.oasipservice.dtos.MatchDTO;
import sit.int221.oasipservice.services.UserService;

@RestController
@RequestMapping("/api")
public class UtilController {
    private final UserService service;

    public UtilController(UserService service) {
        this.service = service;
    }

    @PostMapping("/match")
    public void match(@RequestBody MatchDTO login) { service.match(login); }
}
