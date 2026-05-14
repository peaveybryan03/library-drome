package library.drome.data;

import library.drome.models.FilmList;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ListJdbcClientRepository implements ListRepository {
    private final JdbcClient jdbcClient;

    public ListJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public FilmList create(FilmList list) {
        final String sql = """
                insert into film_list (title, user_id)
                values (:title, :user_id);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("title", list.getTitle())
                .param("user_id", list.getUserId())
                .update(keyHolder, "list_id");

        if (rowsAffected == 0) {
            return null;
        }

        list.setListId(keyHolder.getKey().intValue());
        return list;
    }
}
