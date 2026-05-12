package library.drome.models;

import java.util.Objects;

public class Rating {
    private int ratingId;
    private int value;
    private String notes;
    private int physicalId;

    public Rating(int ratingId, int value, String notes, int physicalId) {
        this.ratingId = ratingId;
        this.value = value;
        this.notes = notes;
        this.physicalId = physicalId;
    }

    public Rating() {

    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(int physicalId) {
        this.physicalId = physicalId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return getRatingId() == rating.getRatingId() && getValue() == rating.getValue() && getPhysicalId() == rating.getPhysicalId() && Objects.equals(getNotes(), rating.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRatingId(), getValue(), getNotes(), getPhysicalId());
    }
}
