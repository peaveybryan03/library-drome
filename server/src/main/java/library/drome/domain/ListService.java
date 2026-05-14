package library.drome.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import library.drome.data.DataAccessException;
import library.drome.data.ListRepository;
import library.drome.models.FilmList;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ListService {
    private final ListRepository repository;
    private final Validator validator;

    public ListService(ListRepository repository, Validator validator) {
        this.repository = repository;
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

        // check for user by id

        if (result.isSuccess()) {
            FilmList created = repository.create(list);
            result.setpayload(created);
        }

        return result;
    }
}
