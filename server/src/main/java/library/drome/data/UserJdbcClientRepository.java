package library.drome.data;

import library.drome.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository {
    private final JdbcClient jdbcClient;

    public UserJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public User findByEmail(String email) {
        return jdbcClient.sql("select * from user where user.email = :email;")
                .param("email", email)
                .query(User.class)
                .optional().orElse(null);
    }

    @Override
    public User findById(int userId) {
        return jdbcClient.sql("select * from user where user.user_id = :user_id;")
                .param("user_id", userId)
                .query(User.class)
                .optional().orElse(null);
    }

    @Override
    public User create(User user) throws DataAccessException {
        final String sql = """
                insert into user (email, password)
                values (:email, :password);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("email", user.getEmail())
                .param("password", user.getPassword())
                .update(keyHolder, "user_id");

        if (rowsAffected == 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());

        return user;
    }
}
