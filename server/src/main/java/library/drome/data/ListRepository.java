package library.drome.data;

import library.drome.models.FilmList;
import library.drome.models.Movie;

import java.util.List;

public interface ListRepository {
    List<FilmList> findByUserId(int userId) throws DataAccessException;

    FilmList findByListId(int listId) throws DataAccessException;

    FilmList create(FilmList list) throws DataAccessException;

    boolean deleteById(int listId) throws DataAccessException;

    boolean addMovieToList(int movieId, int listId) throws DataAccessException;

    boolean removeMovieFromList(int movieId, int listId) throws DataAccessException;

    List<Movie> findMoviesByListId(int listId) throws DataAccessException;
}
