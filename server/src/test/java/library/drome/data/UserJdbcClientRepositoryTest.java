package library.drome.data;

import library.drome.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    private UserJdbcClientRepository repository;

    @Autowired
    private JdbcClient jdbcClient;

    @BeforeEach
    void setup() {
        jdbcClient.sql("call set_known_good_state();").update();
    }

    @Test
    void findByEmailHappyPath() throws DataAccessException {
        User actual = repository.findByEmail("a@a.com");

        assertEquals(TestDataHelper.existingUserFromDatabase(), actual);
    }

    @Test
    void findByEmailFailsToFind() throws DataAccessException {
        User actual = repository.findByEmail("does@not.exist");

        assertNull(actual);
    }

    @Test
    void findByIdHappyPath() throws DataAccessException {
        User actual = repository.findById(1);

        assertEquals(TestDataHelper.existingUserFromDatabase(), actual);
    }

    @Test
    void findByIdFailsToFind() throws DataAccessException {
        User actual = repository.findById(999);

        assertNull(actual);
    }

    @Test
    void shouldCreate() {
        User toCreate = TestDataHelper.userToCreate();
        User expected = TestDataHelper.userToCreate();
        expected.setUserId(3);

        assertNull(repository.findByEmail(toCreate.getEmail()));

        User actual = repository.create(toCreate);

        assertEquals(expected, actual);
        assertNotNull(repository.findByEmail(toCreate.getEmail()));
    }

}