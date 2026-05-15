package library.drome.data.mappers;

import library.drome.models.Availability;
import library.drome.models.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Movie(
                rs.getInt("movie_id"),
                rs.getString("title"),
                rs.getInt("year"),
                Availability.findByName(rs.getString("availability")),
                rs.getString("poster_url")
        );
    }
}
