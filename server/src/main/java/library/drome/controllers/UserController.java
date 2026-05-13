package library.drome.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import library.drome.data.DataAccessException;
import library.drome.domain.Result;
import library.drome.domain.ResultType;
import library.drome.domain.UserService;
import library.drome.models.User;
import library.drome.models.UserWithoutPasswordDto;
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

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) throws DataAccessException, JsonProcessingException {
        Result<User> result = service.authenticate(user);

        if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        } else if (result.getResultType() == ResultType.INVALID) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.UNAUTHORIZED);
        }

        UserWithoutPasswordDto userDto = UserWithoutPasswordDto.fromUser(result.getpayload());

        // turn the result.payload into a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(userDto);

        // concatenate the secret string onto it
        String userJsonWithSecret = userJson + "backend-secret-string"; // irl this should be an env var

        // hash that concatenation result
        int hashTotal = Objects.hash(userJsonWithSecret);

        // make a string of user data AND that total
        String outputString = userJson + "|" + hashTotal;
        Map<String, String> outputMap = Map.of("user", outputString);

        // send to the frontend
        return new ResponseEntity<>(outputMap, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid User user, BindingResult bindingResult) throws DataAccessException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        Result<User> result = service.create(user);

        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }
}
