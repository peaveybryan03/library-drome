package library.drome.data;

import library.drome.data.mappers.MovieMapper;
import library.drome.models.FilmList;
import library.drome.models.Movie;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ListJdbcClientRepository implements ListRepository {
    private final JdbcClient jdbcClient;

    public ListJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<FilmList> findByUserId(int userId) throws DataAccessException {
        final String sql = """
                select * from film_list where user_id = :user_id;
                """;

        return jdbcClient.sql(sql)
                .param("user_id", userId)
                .query(FilmList.class)
                .list();
    }

    @Override
    public FilmList findByListId(int listId) throws DataAccessException {
        final String sql = """
                select * from film_list where list_id = :list_id;
                """;

        FilmList list = jdbcClient.sql(sql)
                .param("list_id", listId)
                .query(FilmList.class)
                .optional()
                .orElse(null);

        if (list != null) {
            list.setMovies(findMoviesByListId(listId));
        }

        return list;
    }

    @Override
    public FilmList create(FilmList list) throws DataAccessException {
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

    @Override
    public boolean deleteById(int listId) throws DataAccessException {
        final String sql = "delete from film_list where list_id = :list_id;";
        return jdbcClient.sql(sql).param("list_id", listId).update() > 0;
    }

    @Override
    public void addMovieToList(int movieId, int listId) throws DataAccessException {
        final String sql = """
                insert into movie_is_on_list (movie_id, list_id) values
                (:movie_id, :list_id);
                """;

        jdbcClient.sql(sql)
                .param("movie_id", movieId)
                .param("list_id", listId)
                .update();
    }

    @Override
    public boolean removeMovieFromList(int movieId, int listId) throws DataAccessException {
        final String sql = """
                delete from movie_is_on_list
                where movie_id = :movie_id and list_id = :list_id;
                """;

        return jdbcClient.sql(sql)
                .param("movie_id", movieId)
                .param("list_id", listId)
                .update() > 0;
    }

    @Override
    public List<Movie> findMoviesByListId(int listId) throws DataAccessException {
        final String sql = """
                select * from movie m
                inner join movie_is_on_list moil on m.movie_id = moil.movie_id
                where moil.list_id = :list_id;
                """;

        return jdbcClient.sql(sql)
                .param("list_id", listId)
                .query(new MovieMapper())
                .list();
    }
}
