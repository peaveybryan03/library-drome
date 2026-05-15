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

    @Test
    void deleteFailsWhenNotFound() {
        when(listRepository.deleteById(999)).thenReturn(false);

        Result<FilmList> actual = service.deleteById(999);

        assertFalse(actual.isSuccess());
    }

    @Test
    void deleteHappyPath() {
        when(listRepository.deleteById(1)).thenReturn(true);

        Result<FilmList> actual = service.deleteById(1);

        assertTrue(actual.isSuccess());
    }

//    @Test
//    void addMovieToListFailsWhenMovieNotFound() {
//
//    }

    @Test
    void addMovieToListFailsWhenListNotFound() {
        when(listRepository.findByListId(1)).thenReturn(null);

        Result<FilmList> actual = service.addMovieToList(1, 999);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("List id 999 was not found."));
    }

    @Test
    void addMovieToListFailsWhenMovieAlreadyOnList() {
        when(listRepository.findByListId(1)).thenReturn(new FilmList());
        when(listRepository.findMoviesByListId(1)).thenReturn(list1());

        Result<FilmList> actual = service.addMovieToList(1, 1);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Movie id 1 is already on list id 1."));
    }

    @Test
    void addMovieToListHappyPath() {
        when(listRepository.findByListId(2)).thenReturn(new FilmList());
        when(listRepository.findMoviesByListId(2)).thenReturn(List.of());

        Result<FilmList> actual = service.addMovieToList(1, 2);

        assertTrue(actual.isSuccess());
    }

    @Test
    void removeMovieFromListFailsWhenMovieNotOnList() {
        when(listRepository.removeMovieFromList(1, 2)).thenReturn(false);

        Result<FilmList> actual = service.removeMovieFromList(1, 2);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Movie id 1 was not found on list id 2."));
    }

    @Test
    void removeMovieFromListHappyPath() {
        when(listRepository.removeMovieFromList(1, 1)).thenReturn(true);

        Result<FilmList> actual = service.removeMovieFromList(1, 1);

        assertTrue(actual.isSuccess());
    }
}