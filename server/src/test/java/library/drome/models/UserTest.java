package library.drome.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void emptyUserShouldFailValidation() {
        User user = new User();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());
    }

    @Test
    void userWithoutEmailShouldFailValidation() {
        User user = makeValidUser();
        user.setEmail("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        ConstraintViolation<User> first = violations.stream().findFirst().orElse(null);
        assertEquals("Email is required.", first.getMessage());
    }

    @Test
    void userWithInvalidEmailShouldFailValidation() {
        User user = makeValidUser();
        user.setEmail("not an email");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        ConstraintViolation<User> first = violations.stream().findFirst().orElse(null);
        assertEquals("Email must be a valid email address.", first.getMessage());
    }

    @Test
    void userWithouPasswordShouldFailValidation() {
        User user = makeValidUser();
        user.setPassword("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());

        ConstraintViolation<User> first = violations.stream().findFirst().orElse(null);
        assertEquals("Password is required.", first.getMessage());
    }

    @Test
    void validUserShouldPassValidation() {
        User user = makeValidUser();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty());
    }

    User makeValidUser() {
        User user = new User();
        user.setEmail("valid@gmail.com");
        user.setPassword("password");
        return user;
    }

}