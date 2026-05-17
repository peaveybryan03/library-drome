package library.drome.data;

import library.drome.models.Genre;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class GenreJdbcClientRepository implements GenreRepository {
    private final JdbcClient jdbcClient;

    public GenreJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Genre findByGenreId(int genreId) throws DataAccessException {
        final String sql = """
                select * from genre where genre_id = :genre_id;
                """;

        return jdbcClient.sql(sql)
                .param("genre_id", genreId)
                .query(Genre.class)
                .optional()
                .orElse(null);
    }

    @Override
    public Genre create(Genre genre) throws DataAccessException {
        final String sql = """
                insert into genre (name)
                values (:name);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("name", genre.getName())
                .update(keyHolder, "genre_id");

        if (rowsAffected == 0) {
            return null;
        }

        genre.setGenreId(keyHolder.getKey().intValue());
        return genre;
    }
}
