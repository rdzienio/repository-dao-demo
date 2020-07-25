package pl.sda.repository.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    }
    @GetMapping("/v2/{pesel}")
    public ResponseEntity<SdaUser> showUserByPeselV2(@PathVariable final String pesel) {
        log.info("Got pesel: {}", pesel);
        SdaUser foundUser = service.getUserByPesel(pesel);
        if (foundUser.getPesel()!=null)
            return ResponseEntity.ok(foundUser);
        return ResponseEntity.noContent().build();
    }

/*    @GetMapping("{pesel}")
    public SdaUser showUserByPeselAndName(@PathVariable final String pesel, @RequestParam String name){
        log.info("Got pesel {} and name {}", pesel, name);
        return service.getUserByPeselAndName(pesel,name);
    }*/


}
