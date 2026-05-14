package library.drome.domain;

import library.drome.data.ListRepository;
import library.drome.data.UserRepository;
import library.drome.models.FilmList;
import library.drome.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static library.drome.data.TestDataHelper.listAfterCreate;
import static library.drome.data.TestDataHelper.listToCreate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ListServiceTest {

    @Autowired
    private ListService service;

    @MockBean
    private ListRepository listRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void createFailsWhenTitleIsBlank() {
        FilmList toCreate = listToCreate();
        toCreate.setTitle("");

        Result<FilmList> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Title is required."));
    }

    @Test
    void createFailsWhenUserDoesNotExist() {
        FilmList toCreate = listToCreate();

        when(userRepository.findById(toCreate.getUserId())).thenReturn(null);

        Result<FilmList> actual = service.create(toCreate);

        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("User not found."));
    }

    @Test
    void createFailsWhenUserAndTitleAreNotUnique() {

    }

    @Test
    void createHappyPath() {
        when(userRepository.findById(anyInt())).thenReturn(new User());
        // more mocking later
        when(listRepository.create(listToCreate())).thenReturn(listAfterCreate());

        Result<FilmList> actual = service.create(listToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(listAfterCreate(), actual.getpayload());
    }
}