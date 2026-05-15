package library.drome.controllers;

import library.drome.data.DataAccessException;
import library.drome.domain.ListService;
import library.drome.models.FilmList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/list")
@CrossOrigin
public class ListController {
    private final ListService service;

    public ListController(ListService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> findByUserId(@PathVariable int userId, @RequestHeader Map<String, String> headers) throws DataAccessException {
        AuthorizationResult authorizationResult = AuthorizationHelper.getUserFromHeaders(headers);

        if (!authorizationResult.isSuccess()) {
            return authorizationResult.getResponseEntity();
        }

        List<FilmList> result = service.findByUserId(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
