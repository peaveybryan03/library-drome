package library.drome.data;

import library.drome.models.FilmList;

import java.util.List;

public interface ListRepository {
    List<FilmList> findByUserId(int userId);

    FilmList create(FilmList list);

    boolean deleteById(int listId);
}
