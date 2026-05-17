package library.drome.domain;

import library.drome.data.MovieRepository;
import library.drome.models.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static library.drome.data.TestDataHelper.movieAfterCreate;
import static library.drome.data.TestDataHelper.movieToCreate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieServiceTest {

    @Autowired
    private MovieService service;

    @MockBean
    private MovieRepository movieRepository;

//    @MockBean
//    private GenreRepository genreRepository;
//
//    @MockBean
//    private DirectorRepository directorRepository;

    @Test
    void createFailsWhenTitleIsBlank() {
        Movie toCreate = movieToCreate();
        toCreate.setTitle("");

        Result<Movie> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Title is required."));
    }

    @Test
    void createFailsWhenYearIsBefore1880() {
        Movie toCreate = movieToCreate();
        toCreate.setYear(1879);

        Result<Movie> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Year must be 1880 or later."));
    }

    @Test
    void createFailsWhenAvailabilityIsNotSet() {
        Movie toCreate = movieToCreate();
        toCreate.setAvailability(null);

        Result<Movie> actual = service.create(toCreate);

        assertEquals(ResultType.INVALID, actual.getResultType());
        assertTrue(actual.getErrorMessages().contains("Availability is required."));
    }

    @Test
    void createHappyPath() {
        when(movieRepository.create(movieToCreate())).thenReturn(movieAfterCreate());

        Result<Movie> actual = service.create(movieToCreate());

        assertTrue(actual.isSuccess());
        assertEquals(movieAfterCreate(), actual.getpayload());
    }

}