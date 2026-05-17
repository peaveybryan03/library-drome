package library.drome.data;

import library.drome.models.Genre;

public interface GenreRepository {
    Genre findByGenreId(int genreId) throws DataAccessException;

    Genre create(Genre genre) throws DataAccessException;
}
