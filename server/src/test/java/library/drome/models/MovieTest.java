package library.drome.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void emptyMovieShouldFailValidation() {
        Movie movie = new Movie();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        assertFalse(violations.isEmpty());
    }

    @Test
    void movieWithoutTitleShouldFailValidation() {
        Movie movie = makeValidMovie();
        movie.setTitle("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        assertEquals(1, violations.size());

        ConstraintViolation<Movie> first = violations.stream().findFirst().orElse(null);
        assertEquals("Title is required.", first.getMessage());
    }

    @Test
    void movieWithEarlyYearShouldFailValidation() {
        Movie movie = makeValidMovie();
        movie.setYear(1879);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        assertEquals(1, violations.size());

        ConstraintViolation<Movie> first = violations.stream().findFirst().orElse(null);
        assertEquals("Year must be 1880 or later.", first.getMessage());
    }

    @Test
    void movieWithNullAvailabilityShouldFailValidation() {
        Movie movie = makeValidMovie();
        movie.setAvailability(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        assertEquals(1, violations.size());

        ConstraintViolation<Movie> first = violations.stream().findFirst().orElse(null);
        assertEquals("Availability is required.", first.getMessage());
    }

    @Test
    void validMovieShouldPassValidation() {
        Movie movie = makeValidMovie();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);

        assertTrue(violations.isEmpty());
    }

    Movie makeValidMovie() {
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setYear(2003);
        movie.setAvailability(Availability.BOTH);
        movie.setPosterUrl("url");
        return movie;
    }

}