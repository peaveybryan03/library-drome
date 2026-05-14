package library.drome.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FilmListTest {

    @Test
    void emptyListShouldFailValidation() {
        FilmList list = new FilmList();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FilmList>> violations = validator.validate(list);

        assertFalse(violations.isEmpty());
    }

    @Test
    void listWithoutTitleShouldFailValidation() {
        FilmList list = makeValidList();
        list.setTitle("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FilmList>> violations = validator.validate(list);

        assertEquals(1, violations.size());

        ConstraintViolation<FilmList> first = violations.stream().findFirst().orElse(null);
        assertEquals("Title is required.", first.getMessage());
    }

    @Test
    void validListShouldPassValidation() {
        FilmList list = makeValidList();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FilmList>> violations = validator.validate(list);

        assertTrue(violations.isEmpty());
    }

    FilmList makeValidList() {
        FilmList list = new FilmList();
        list.setTitle("title");
        list.setUserId(1);
        return list;
    }
}