package library.drome.domain;

import library.drome.data.ListRepository;
import library.drome.data.UserRepository;
import library.drome.models.FilmList;
import library.drome.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static library.drome.data.TestDataHelper.*;
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
        FilmList toCreate = listToCreate();

        when(userRepository.findById(toCreate.getUserId())).thenReturn(existingUser());
        when(listRepository.findByUserId(toCreate.getUserId())).thenReturn(List.of(listAfterCreate()));

        Result<FilmList> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("User already has a list with this title."));
    }

    @Test
    void createHappyPath() {
        when(userRepository.findById(anyInt())).thenReturn(new User());
        when(listRepository.findByUserId(anyInt())).thenReturn(List.of());
        when(listRepository.create(listToCreate())).thenReturn(listAfterCreate());

        Result<FilmList> actual = service.create(listToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(listAfterCreate(), actual.getpayload());
    }
}