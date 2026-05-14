package library.drome.data;

import library.drome.models.FilmList;
import org.springframework.jdbc.core.simple.JdbcClient;

public class ListJdbcClientRepository implements ListRepository {
    private final JdbcClient jdbcClient;

    public ListJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public FilmList create(FilmList list) {
        return null;
    }
}
