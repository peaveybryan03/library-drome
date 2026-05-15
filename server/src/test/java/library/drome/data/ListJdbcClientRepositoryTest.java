package library.drome.data;

import library.drome.models.FilmList;
import library.drome.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ListJdbcClientRepositoryTest {

    @Autowired
    private ListJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void findByUserIdHappyPath() {
        List<FilmList> actual = repository.findByUserId(1);

        assertFalse(actual.isEmpty());
        assertEquals(List.of(TestDataHelper.existingList()), actual);
    }

    @Test
    void findByUserIdFailsToFind() {
        List<FilmList> actual = repository.findByUserId(999);

        assertTrue(actual.isEmpty());
    }

    @Test
    void findByListIdHappyPath() {
        FilmList actual = repository.findByListId(1);

        assertNotNull(actual);
        assertEquals(TestDataHelper.existingListWithMoviesAttached(), actual);
    }

    @Test
    void findByListIdFailsToFind() {
        FilmList actual = repository.findByListId(999);

        assertNull(actual);
    }

    @Test
    void shouldCreate() {
        FilmList toCreate = TestDataHelper.listToCreate();
        FilmList expected = TestDataHelper.listAfterCreate();

        assertFalse(repository.findByUserId(toCreate.getUserId()).contains(TestDataHelper.listAfterCreate()));

        FilmList actual = repository.create(toCreate);

        assertEquals(expected, actual);
        assertTrue(repository.findByUserId(toCreate.getUserId()).contains(TestDataHelper.listAfterCreate()));
    }

    @Test
    void shouldDelete() {
        boolean actual = repository.deleteById(1);

        assertTrue(actual);
        assertTrue(repository.findByUserId(1).isEmpty());
    }

    @Test
    void shouldNotDeleteWhenNotFound() {
        boolean actual = repository.deleteById(999);

        assertFalse(actual);
    }

    @Test
    void shouldAddMovieToList() {
        repository.addMovieToList(1, 2);

        assertTrue(repository.findMoviesByListId(2).contains(TestDataHelper.theDoomGeneration()));
    }

    @Test
    void shouldRemoveMovieFromList() {
        boolean actual = repository.removeMovieFromList(1, 1);

        assertTrue(actual);
        assertFalse(repository.findMoviesByListId(1).contains(TestDataHelper.theDoomGeneration()));
    }

    @Test
    void shouldNotRemoveMovieFromListWhenNotOnList() {
        boolean actual = repository.removeMovieFromList(1, 2);

        assertFalse(actual);
    }

    @Test
    void findMoviesByListIdHappyPath() {
        List<Movie> actual = repository.findMoviesByListId(1);

        assertFalse(actual.isEmpty());
        assertEquals(4, actual.size());
    }

    @Test
    void findMoviesByListIdEmptyList() {
        List<Movie> actual = repository.findMoviesByListId(2);

        assertTrue(actual.isEmpty());
    }
}