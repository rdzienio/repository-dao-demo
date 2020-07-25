package pl.sda.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.sda.repository.SdaUser;
import pl.sda.repository.service.UserService;

import java.util.List;

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
    public SdaUser showUserByPesel(@PathVariable final String pesel) {
        log.info("Got pesel: {}", pesel);
        return service.getUserByPesel(pesel);
/*        var result = service.getUserByPesel(pesel);
        if (result.isPresent()) {
            log.info("isPresent");
            return ResponseEntity.of(result);
        }
        else {
            log.info("is not");
            return new ResponseEntity(new SdaUser(), HttpStatus.NO_CONTENT);
        }*/

    }


}
