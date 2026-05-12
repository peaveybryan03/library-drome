package library.drome.models;

import java.util.Objects;

public class Physical {
    private int physicalId;
    private int movieId;
    private Format format;
    private int year;

    public Physical(int physicalId, int movieId, Format format, int year) {
        this.physicalId = physicalId;
        this.movieId = movieId;
        this.format = format;
        this.year = year;
    }

    public int getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(int physicalId) {
        this.physicalId = physicalId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Physical physical = (Physical) o;
        return getPhysicalId() == physical.getPhysicalId() && getMovieId() == physical.getMovieId() && getYear() == physical.getYear() && getFormat() == physical.getFormat();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhysicalId(), getMovieId(), getFormat(), getYear());
    }
}
