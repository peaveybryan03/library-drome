package library.drome.data;

import library.drome.models.Movie;

public interface MovieRepository {
    Movie findByMovieId(int movieId) throws DataAccessException;

    Movie create(Movie movie) throws DataAccessException;
}
