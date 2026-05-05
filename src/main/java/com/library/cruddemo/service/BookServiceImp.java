package com.library.cruddemo.service;

import com.library.cruddemo.dao.AuthorRepository;
import com.library.cruddemo.dao.BookRepository;
import com.library.cruddemo.dao.LibRepository;
import com.library.cruddemo.dto.BookDTO;
import com.library.cruddemo.entity.Author;
import com.library.cruddemo.entity.Book;
import com.library.cruddemo.entity.Lib;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.mapper.LibraryMapper;
import com.library.cruddemo.service.interfaces.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;
    private final LibRepository libRepository;
    private final AuthorRepository authorRepository;
    private final LibraryMapper libraryMapper;

    public BookServiceImp(BookRepository bookRepository, LibRepository libRepository, AuthorRepository authorRepository, LibraryMapper libraryMapper) {
        this.bookRepository = bookRepository;
        this.libRepository = libRepository;
        this.authorRepository = authorRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(libraryMapper::toBookDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(int bookId) throws ServiceException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException("Book not found for id - " + bookId, HttpStatus.NOT_FOUND));
        return libraryMapper.toBookDTO(book);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public BookDTO saveBook(BookDTO bookDTO) throws ServiceException {
        Book book = libraryMapper.toBookEntity(bookDTO);
        book.setId(0);
        return libraryMapper.toBookDTO(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void assignBookToLib(int bookId, int libId) throws ServiceException {
        Lib lib = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Library not found for id - " + libId, HttpStatus.NOT_FOUND));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException("Book not found for id - " + bookId, HttpStatus.NOT_FOUND));

        lib.getBooks().add(book);
        if (!book.isAvailability()) book.setAvailability(true);

        libRepository.save(lib);
    }

    @Override
    @Transactional
    public void unassignBookFromLib(int bookId, int libId) throws ServiceException {
        Lib lib = libRepository.findLibByBookId(bookId, libId)
                .orElseThrow(() -> new ServiceException("Book with id - " + bookId + " is not assigned to the library with id - " + libId, HttpStatus.NOT_FOUND));

        Book book = lib.getBooks().stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElseThrow();

        boolean toGloballyUnassign = book.getLibraries().stream().noneMatch(l -> l.getId() != libId);
        if (toGloballyUnassign) {
            book.setAvailability(false);
            bookRepository.save(book);
        }

        lib.getBooks().removeIf(b -> b.getId() == bookId);
        libRepository.save(lib);
    }

    @Override
    @Transactional
    public void assignAuthorToBook(int authorId, int bookId) throws ServiceException {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ServiceException("Author with id - " + authorId + " not found", HttpStatus.NOT_FOUND));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException("Book with id - " + bookId + " not found", HttpStatus.NOT_FOUND));

        book.getAuthors().add(author);

        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void unassignAuthorFromBook(int authorId, int bookId) throws ServiceException {

        Book book = bookRepository.findBookByAuthorId(bookId, authorId)
                .orElseThrow(() -> new ServiceException("Book with id - " + bookId + " has no author assigned with id - " + authorId, HttpStatus.NOT_FOUND));

        book.getAuthors().removeIf(a -> a.getId() == authorId);
        bookRepository.save(book);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteBookById(int bookId) throws ServiceException {
        Book toDelete = bookRepository.findById(bookId)
                .orElseThrow(() -> new ServiceException("Book not found for id - " + bookId, HttpStatus.NOT_FOUND));
        bookRepository.delete(toDelete);
    }
}
