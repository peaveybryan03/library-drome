package library.drome.domain;

import library.drome.data.GenreRepository;
import library.drome.models.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static library.drome.data.TestDataHelper.genreAfterCreate;
import static library.drome.data.TestDataHelper.genreToCreate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GenreServiceTest {

    @Autowired
    private GenreService service;

    @MockBean
    private GenreRepository repository;

    @Test
    void createFailsWhenNameIsBlank() {
        Genre toCreate = genreToCreate();
        toCreate.setName("");

        Result<Genre> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Name is required."));
    }

    @Test
    void createHappyPath() {
        when(repository.create(genreToCreate())).thenReturn(genreAfterCreate());

        Result<Genre> actual = service.create(genreToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(genreAfterCreate(), actual.getpayload());
    }

}