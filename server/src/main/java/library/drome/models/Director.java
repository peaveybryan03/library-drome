package library.drome.models;

import java.util.Objects;

public class Director {
    private int directorId;
    private String name;

    public Director(int directorId, String name) {
        this.directorId = directorId;
        this.name = name;
    }

    public Director() {

    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return getDirectorId() == director.getDirectorId() && Objects.equals(getName(), director.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDirectorId(), getName());
    }
}
