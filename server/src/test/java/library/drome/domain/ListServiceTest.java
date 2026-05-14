package library.drome.domain;

import library.drome.data.ListRepository;
import library.drome.models.FilmList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static library.drome.data.TestDataHelper.listAfterCreate;
import static library.drome.data.TestDataHelper.listToCreate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ListServiceTest {

    @Autowired
    private ListService service;

    @MockBean
    private ListRepository repository;

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

    }

    @Test
    void createFailsWhenUserAndTitleAreNotUnique() {

    }

    @Test
    void createHappyPath() {
        // additional mocking necessary after findBy
        when(repository.create(listToCreate())).thenReturn(listAfterCreate());

        Result<FilmList> actual = service.create(listToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(listAfterCreate(), actual.getpayload());
    }
}