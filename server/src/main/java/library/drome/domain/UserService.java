package library.drome.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import library.drome.data.DataAccessException;
import library.drome.data.UserRepository;
import library.drome.models.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final Validator validator;

    public UserService(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Result<User> create(User user) throws DataAccessException {
        Result<User> result = new Result<>();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<User> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

//        if (repository.findByEmail(user.getEmail()) != null) {
//            result.addErrorMessage("Email is already taken", ResultType.INVALID);
//        }

        if (result.isSuccess()) {
            int hashedPassword = Objects.hash(user.getPassword());
            String hashedPasswordString = String.valueOf(hashedPassword);
            user.setPassword(hashedPasswordString);

            User created = repository.create(user);
            result.setpayload(created);
        }

        return result;
    }
}
