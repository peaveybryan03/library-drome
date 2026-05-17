package library.drome.data;

import library.drome.models.Genre;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class GenreJdbcClientRepository implements GenreRepository {
    private final JdbcClient jdbcClient;

    public GenreJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Genre findByGenreId(int genreId) throws DataAccessException {
        return null;
    }

    @Override
    public Genre create(Genre genre) throws DataAccessException {
        return null;
    }
}
