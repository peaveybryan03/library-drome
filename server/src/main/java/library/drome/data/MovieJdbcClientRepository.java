package library.drome.data;

import library.drome.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;

public class MovieJdbcClientRepository implements MovieRepository {
    private final JdbcClient jdbcClient;

    public MovieJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Movie findById(int movieId) throws DataAccessException {
        return null;
    }

    @Override
    public Movie create(Movie movie) throws DataAccessException {
        return null;
    }
}
