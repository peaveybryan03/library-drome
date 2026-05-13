package library.drome.domain;

import library.drome.data.DataAccessException;
import library.drome.data.TestDataHelper;
import library.drome.data.UserRepository;
import library.drome.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static library.drome.data.TestDataHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Test
    void authenticateHappyPath() throws DataAccessException {
        User proposedUserLogin = TestDataHelper.existingUser();
        proposedUserLogin.setUserId(0);
        User userFromDatabase = TestDataHelper.existingUserFromDatabase();
        when(repository.findByEmail(proposedUserLogin.getEmail())).thenReturn(userFromDatabase);

        Result<User> actual = service.authenticate(proposedUserLogin);

        assertTrue(actual.isSuccess());
        assertEquals(userFromDatabase, actual.getpayload());
    }

    @Test
    void authenticateEmailNotFound() throws DataAccessException {
        User proposedUserLogin = TestDataHelper.existingUser();
        proposedUserLogin.setUserId(0);
        when(repository.findByEmail(proposedUserLogin.getEmail())).thenReturn(null);

        Result<User> actual = service.authenticate(proposedUserLogin);

        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertNull(actual.getpayload());
        assertEquals("User does not exist.", actual.getErrorMessages().get(0));
    }

    @Test
    void authenticateWrongPassword() throws DataAccessException {
        User proposedUserLogin = TestDataHelper.existingUser();
        proposedUserLogin.setUserId(0);
        proposedUserLogin.setPassword("incorrect");
        User userFromDatabase = existingUserFromDatabase();
        when(repository.findByEmail(proposedUserLogin.getEmail())).thenReturn(userFromDatabase);

        Result<User> actual = service.authenticate(proposedUserLogin);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertNull(actual.getpayload());
        assertEquals("Incorrect password.", actual.getErrorMessages().get(0));
    }

    @Test
    void createFailsWhenEmailIsBlank() throws DataAccessException {
        User toCreate = userToCreate();
        toCreate.setEmail("");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email is required."));
    }

    @Test
    void createFailsWhenEmailIsInvalid() throws DataAccessException {
        User toCreate = userToCreate();
        toCreate.setEmail("not an email");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email must be a valid email address."));
    }

    @Test
    void createFailsWhenPasswordIsBlank() throws DataAccessException {
        User toCreate = userToCreate();
        toCreate.setPassword("");

        Result<User> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Password is required."));
    }

    @Test
    void createFailsWhenEmailIsDuplicate() throws DataAccessException {
        when(repository.findByEmail(userToCreate().getEmail())).thenReturn(existingUser());

        Result<User> actual = service.create(userToCreate());

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Email is already taken."));
    }

    @Test
    void createHappyPath() throws DataAccessException {
        when(repository.create(any(User.class))).thenReturn(userAfterCreate());

        Result<User> actual = service.create(userToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(userAfterCreate(), actual.getpayload());
    }

}