package library.drome.data;

import library.drome.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MovieJdbcClientRepositoryTest {

    @Autowired
    private MovieJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void findByIdHappyPath() {
        Movie expected = TestDataHelper.theDoomGeneration();

        Movie actual = repository.findByMovieId(1);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByIdNotFound() {
        Movie actual = repository.findByMovieId(999);

        assertNull(actual);
    }

    @Test
    void shouldCreate() {
        Movie toCreate = TestDataHelper.movieToCreate();
        Movie expected = TestDataHelper.movieAfterCreate();

        assertNull(repository.findByMovieId(expected.getMovieId()));

        Movie actual = repository.create(toCreate);

        assertEquals(expected, actual);
        assertNotNull(repository.findByMovieId(expected.getMovieId()));
    }


}