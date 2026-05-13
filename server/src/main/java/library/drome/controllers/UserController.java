package library.drome.controllers;

import jakarta.validation.Valid;
import library.drome.data.DataAccessException;
import library.drome.domain.Result;
import library.drome.domain.UserService;
import library.drome.models.User;
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
