package com.library.cruddemo.rest;

import com.library.cruddemo.dto.AuthorDTO;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.service.interfaces.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Author", description = "Operations for Authors")
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // ====== GET MAPPINGS ======

    @GetMapping("/authors")
    public List<AuthorDTO> getAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/authors/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable int authorId) throws ServiceException {
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }

    // ====== POST MAPPINGS ======

    @PostMapping("/authors")
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.saveAuthor(authorDTO));
    }

    // ====== DELETE MAPPINGS ======

    @DeleteMapping("/authors/{authorId}")
    public ResponseEntity<String> deleteAuthorById(@PathVariable int authorId) throws ServiceException {
        authorService.deleteAuthorById(authorId);
        return ResponseEntity.ok().body("Deleted author with id - " + authorId);
    }
}
