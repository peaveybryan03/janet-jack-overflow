package learn.janet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import learn.janet.data.DataAccessException;
import learn.janet.domain.Result;
import learn.janet.domain.ResultType;
import learn.janet.domain.UserService;
import learn.janet.models.User;
import learn.janet.models.UserNoPasswordDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> findByEmail(@PathVariable("email") String email) throws DataAccessException {
        Result<User> result = service.findByEmail(email);

        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> findByName(@PathVariable("name") String name) throws DataAccessException {
        Result<User> result = service.findByName(name);

        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid User user, BindingResult bindingResult) throws JsonProcessingException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<User> serviceResult = service.authenticate(user);

        if (serviceResult.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(serviceResult.getErrorMessages(), HttpStatus.NOT_FOUND);
        } else if (serviceResult.getResultType() == ResultType.INVALID) {
            return new ResponseEntity<>(serviceResult.getErrorMessages(), HttpStatus.UNAUTHORIZED);
        }

        UserNoPasswordDto userDto = UserNoPasswordDto.fromUser(serviceResult.getPayload());

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDto);

        String userJsonWithSecretString = userJson + "this is a secret";

        int hashTotal = Objects.hash(userJsonWithSecretString);

        String outputString = userJson + "|" + hashTotal;
        Map<String, String> outputMap = Map.of("user", outputString);

        return new ResponseEntity<>(outputMap, HttpStatus.OK);
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
