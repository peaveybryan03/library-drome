package library.drome.models;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Objects;

public class FilmList {
    private int listId;

    @NotBlank(message = "Title is required.")
    private String title;
    private int userId;

    private List<Movie> movies;

    public FilmList(int listId, String title, int userId) {
        this.listId = listId;
        this.title = title;
        this.userId = userId;
    }

    public FilmList() {

    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FilmList filmList = (FilmList) o;
        return getListId() == filmList.getListId() && getUserId() == filmList.getUserId() && Objects.equals(getTitle(), filmList.getTitle()) && Objects.equals(getMovies(), filmList.getMovies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListId(), getTitle(), getUserId(), getMovies());
    }
}
