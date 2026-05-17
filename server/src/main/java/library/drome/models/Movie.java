package library.drome.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class Movie {

    private int movieId;

    @NotBlank(message = "Title is required.")
    private String title;

    @Min(value = 1880, message = "Year must be 1880 or later.")
    private int year;

    @NotNull(message = "Availability is required.")
    private Availability availability;

    private String posterUrl;

    private List<Genre> genres;
    private List<Director> directors;

    public Movie(int movieId, String title, int year, Availability availability, String posterUrl) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.availability = availability;
        this.posterUrl = posterUrl;
    }

    public Movie(int movieId, String title, int year) {
        this.movieId = movieId;
        this.title = title;
        this.year = year;
    }

    public Movie() {

    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getMovieId() == movie.getMovieId() && getYear() == movie.getYear() && Objects.equals(getTitle(), movie.getTitle()) && getAvailability() == movie.getAvailability() && Objects.equals(getPosterUrl(), movie.getPosterUrl()) && Objects.equals(getGenres(), movie.getGenres()) && Objects.equals(getDirectors(), movie.getDirectors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieId(), getTitle(), getYear(), getAvailability(), getPosterUrl(), getGenres(), getDirectors());
    }
}
