package library.drome.data;

import library.drome.models.Movie;

public interface MovieRepository {
    Movie findById(int movieId) throws DataAccessException;

    Movie create(Movie movie) throws DataAccessException;
}
