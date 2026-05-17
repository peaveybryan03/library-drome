package library.drome.data;

import library.drome.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GenreJdbcClientRepositoryTest {

    @Autowired
    private GenreJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void findByGenreIdHappyPath() {
        Genre expected = TestDataHelper.horror();

        Genre actual = repository.findByGenreId(11);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void findByGenreIdNotFound() {
        Genre actual = repository.findByGenreId(999);

        assertNull(actual);
    }

    @Test
    void shouldCreate() {
        Genre toCreate = TestDataHelper.genreToCreate();
        Genre expected = TestDataHelper.genreAfterCreate();

        assertNull(repository.findByGenreId(19));

        Genre actual = repository.create(toCreate);

        assertEquals(expected, actual);
        assertNotNull(repository.findByGenreId(19));
    }

}