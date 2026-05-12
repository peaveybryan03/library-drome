package library.drome.models;

import java.util.Objects;

public class Movie {
    private int movie_id;
    private String title;
    private int year;
    private Availability availability;
    private String posterUrl;

    public Movie(int movie_id, String title, int year, Availability availability, String posterUrl) {
        this.movie_id = movie_id;
        this.title = title;
        this.year = year;
        this.availability = availability;
        this.posterUrl = posterUrl;
    }

    public Movie(int movie_id, String title, int year) {
        this.movie_id = movie_id;
        this.title = title;
        this.year = year;
    }

    public Movie() {

    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return getMovie_id() == movie.getMovie_id() && getYear() == movie.getYear() && Objects.equals(getTitle(), movie.getTitle()) && getAvailability() == movie.getAvailability() && Objects.equals(getPosterUrl(), movie.getPosterUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie_id(), getTitle(), getYear(), getAvailability(), getPosterUrl());
    }
}
