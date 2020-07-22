package pl.sda.repository.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.repository.SdaUser;
import pl.sda.repository.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService service;

    public UserRestController(final UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<SdaUser> showAllUsers(){
        return service.getAllUsers();
    }
}
