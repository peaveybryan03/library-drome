package library.drome.data;

import library.drome.models.FilmList;
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
    void shouldCreate() {
        FilmList toCreate = TestDataHelper.listToCreate();
        FilmList expected = TestDataHelper.listAfterCreate();

        assertFalse(repository.findByUserId(toCreate.getUserId()).contains(TestDataHelper.listAfterCreate()));

        FilmList actual = repository.create(toCreate);

        assertEquals(expected, actual);
        assertTrue(repository.findByUserId(toCreate.getUserId()).contains(TestDataHelper.listAfterCreate()));
    }
}