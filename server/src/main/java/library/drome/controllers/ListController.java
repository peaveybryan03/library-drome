package library.drome.controllers;

import library.drome.domain.ListService;
import library.drome.models.FilmList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/list")
@CrossOrigin
public class ListController {
    private final ListService service;

    public ListController(ListService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<FilmList> findByUserId(@PathVariable int userId) {
        // should be authorized
        return service.findByUserId(userId);
    }

}
