package library.drome.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void emptyGenreShouldFailValidation() {
        Genre genre = new Genre();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);

        assertFalse(violations.isEmpty());
    }

    @Test
    void genreWithoutNameShouldFailValidation() {
        Genre genre = makeValidGenre();
        genre.setName("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);

        assertEquals(1, violations.size());

        ConstraintViolation<Genre> first = violations.stream().findFirst().orElse(null);
        assertEquals("Name is required.", first.getMessage());
    }

    @Test
    void validGenreShouldPassValidation() {
        Genre genre = makeValidGenre();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);

        assertTrue(violations.isEmpty());
    }

    Genre makeValidGenre() {
        Genre genre = new Genre();
        genre.setName("name");
        return genre;
    }

}