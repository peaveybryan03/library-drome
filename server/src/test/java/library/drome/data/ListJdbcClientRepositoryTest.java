package library.drome.data;

import library.drome.models.FilmList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

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
    void shouldCreate() {
        FilmList toCreate = TestDataHelper.listToCreate();
        FilmList expected = TestDataHelper.listAfterCreate();

        // assert not present in repo w/ findBy

        FilmList actual = repository.create(toCreate);

        assertEquals(expected, actual);
        // assert present in repo w/ findBy
    }
}