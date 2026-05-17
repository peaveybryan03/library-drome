package library.drome.controllers;

import library.drome.data.DataAccessException;
import library.drome.domain.MovieService;
import library.drome.domain.Result;
import library.drome.models.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
@CrossOrigin
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Object> findByMovieId(@PathVariable int movieId) throws DataAccessException {
        Movie movie = service.findByMovieId(movieId);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> create(@RequestBody Movie movie) throws DataAccessException {
        Result<Movie> result = service.create(movie);
        if (!result.isSuccess()) {
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(result.getpayload(), HttpStatus.CREATED);
    }
}
