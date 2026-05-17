package library.drome.data;

import library.drome.data.mappers.MovieMapper;
import library.drome.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MovieJdbcClientRepository implements MovieRepository {
    private final JdbcClient jdbcClient;

    public MovieJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Movie findByMovieId(int movieId) throws DataAccessException {
        final String sql = """
                select * from movie where movie_id = :movie_id;
                """;

        Movie movie = jdbcClient.sql(sql)
                .param("movie_id", movieId)
                .query(new MovieMapper())
                .optional()
                .orElse(null);

//        if (movie != null) {
//            movie.setGenres(null);
//            movie.setDirectors(null);
//        }

        return movie;
    }

    @Override
    public Movie create(Movie movie) throws DataAccessException {
        final String sql = """
                insert into movie (title, `year`, availability, poster_url)
                values (:title, :year, :availability, :poster_url);
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = jdbcClient.sql(sql)
                .param("title", movie.getTitle())
                .param("year", movie.getYear())
                .param("availability", movie.getAvailability().getDatabaseName())
                .param("poster_url", movie.getPosterUrl())
                .update(keyHolder, "movie_id");

        if (rowsAffected == 0) {
            return null;
        }

        movie.setMovieId(keyHolder.getKey().intValue());
        return movie;
    }
}
