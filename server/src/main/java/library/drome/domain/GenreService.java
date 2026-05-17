package library.drome.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import library.drome.data.DataAccessException;
import library.drome.data.GenreRepository;
import library.drome.models.Genre;
import library.drome.models.Movie;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GenreService {
    private final GenreRepository repository;
    private final Validator validator;

    public GenreService(GenreRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Genre findByGenreId(int genreId) throws DataAccessException {
        return repository.findByGenreId(genreId);
    }

    public Result<Genre> create(Genre genre) throws DataAccessException {
        Result<Genre> result = new Result<>();

        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Genre> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        Genre created = repository.create(genre);
        result.setpayload(created);

        return result;
    }
}
