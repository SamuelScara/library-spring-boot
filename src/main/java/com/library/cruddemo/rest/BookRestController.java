package com.library.cruddemo.rest;

import com.library.cruddemo.dto.BookDTO;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.service.interfaces.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Book", description = "Operations for Books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    // ====== GET MAPPINGS ======

    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int bookId) throws ServiceException {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    // ====== POST MAPPINGS ======

    @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookDTO));
    }

    @PostMapping("/libs/{libId}/books/{bookId}")
    public ResponseEntity<String> assignBookToLib(@PathVariable("libId") int libId, @PathVariable("bookId") int bookId) throws ServiceException {
        bookService.assignBookToLib(bookId, libId);
        return ResponseEntity.ok().body("Assigned book with id - " + bookId + " to library with id - " + libId);
    }

    @PostMapping("/books/{bookId}/authors/{authorId}")
    public ResponseEntity<String> assignAuthorToBook(@PathVariable("bookId") int bookId, @PathVariable("authorId") int authorId) throws ServiceException {
        bookService.assignAuthorToBook(authorId, bookId);
        return ResponseEntity.ok().body("Assigned author with id - " + authorId + " to book with id - " + bookId);
    }


    // ====== DELETE MAPPINGS ======

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId) throws ServiceException {
        bookService.deleteBookById(bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/libs/{libId}/books/{bookId}")
    public ResponseEntity<String> unassignBookFromLib(@PathVariable("libId") int libId, @PathVariable("bookId") int bookId) throws ServiceException {
        bookService.unassignBookFromLib(bookId, libId);
        return ResponseEntity.ok().body("Unassigned book with id - " + bookId + " from library with id - " + libId);
    }

    @DeleteMapping("/books/{bookId}/authors/{authorId}")
    public ResponseEntity<String> unassignAuthorFromBook(@PathVariable("bookId") int bookId, @PathVariable("authorId") int authorId) throws ServiceException {
        bookService.unassignAuthorFromBook(authorId, bookId);
        return ResponseEntity.ok().body("Unassigned author with id - " + authorId + " from book with id - " + bookId);
    }
}
