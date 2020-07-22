package pl.sda.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.repository.SdaUser;
import pl.sda.repository.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService service;

    public UserRestController(final UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<SdaUser> showAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("{pesel}")
    public ResponseEntity<SdaUser> showUserByPesel(@PathVariable final String pesel) {
        log.info("Got pesel: {}", pesel);
        var result = service.getUserByPesel(pesel);
        if (result.isPresent()) {
            log.info("isPresent");
            return ResponseEntity.of(result);
        }
        else {
            log.info("is not");
            return new ResponseEntity(new SdaUser(), HttpStatus.NO_CONTENT);
        }
    }


}
