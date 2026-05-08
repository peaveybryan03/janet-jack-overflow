package learn.janet.controllers;

import jakarta.validation.Valid;
import learn.janet.domain.Result;
import learn.janet.domain.UserService;
import learn.janet.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<User> serviceResult = service.create(user);

        if (!serviceResult.isSuccess()) {
            return ErrorResponse.build(serviceResult);
        }

        return new ResponseEntity<>(serviceResult.getPayload(), HttpStatus.CREATED);
    }
}
