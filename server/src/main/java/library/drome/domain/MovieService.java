package library.drome.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import library.drome.data.DataAccessException;
import library.drome.data.MovieRepository;
import library.drome.models.Movie;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
//    private final GenreRepository genreRepository;
//    private final DirectorRepository directorRepository;
    private final Validator validator;

    public MovieService(MovieRepository movieRepository, Validator validator) {
        this.movieRepository = movieRepository;
        this.validator = validator;
    }

    public Movie findByMovieId(int movieId) throws DataAccessException {
        return movieRepository.findByMovieId(movieId);
    }

    public Result<Movie> create(Movie movie) throws DataAccessException {
        Result<Movie> result = new Result<>();

        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<Movie> violation : violations) {
                result.addErrorMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }

        Movie created = movieRepository.create(movie);
        result.setpayload(created);

        return result;
    }
}
