package library.drome.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import library.drome.data.DataAccessException;
import library.drome.data.ListRepository;
import library.drome.data.UserRepository;
import library.drome.models.FilmList;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ListService {
    private final ListRepository listRepository;
    private final UserRepository userRepository;
    private final Validator validator;

    public ListService(ListRepository listRepository, UserRepository userRepository, Validator validator) {
        this.listRepository = listRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    public Result<FilmList> create(FilmList list) throws DataAccessException {
        Result<FilmList> result = new Result<>();

        Set<ConstraintViolation<FilmList>> violations = validator.validate(list);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<FilmList> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        if (userRepository.findById(list.getUserId()) == null) {
            result.addErrorMessage("User not found.", ResultType.NOT_FOUND);
        }

        if (result.isSuccess()) {
            FilmList created = listRepository.create(list);
            result.setpayload(created);
        }

        return result;
    }
}
