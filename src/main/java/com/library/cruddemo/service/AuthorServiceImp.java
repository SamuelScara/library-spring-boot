package com.library.cruddemo.service;

import com.library.cruddemo.dao.AuthorRepository;
import com.library.cruddemo.dto.AuthorDTO;
import com.library.cruddemo.entity.Author;
import com.library.cruddemo.entity.Book;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.mapper.LibraryMapper;
import com.library.cruddemo.service.interfaces.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements AuthorService {

    private final AuthorRepository authorRepository;
    private final LibraryMapper libraryMapper;

    public AuthorServiceImp(AuthorRepository authorRepository, LibraryMapper libraryMapper) {
        this.authorRepository = authorRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(libraryMapper::toAuthorDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDTO getAuthorById(int id) throws ServiceException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Author with id - " + id + " not found", HttpStatus.NOT_FOUND));
        return libraryMapper.toAuthorDTO(author);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) throws ServiceException {
        Author author = libraryMapper.toAuthorEntity(authorDTO);
        author.setId(0);
        return libraryMapper.toAuthorDTO(authorRepository.save(author));
    }


    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteAuthorById(int id) throws ServiceException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Author with id - " + id + " not found", HttpStatus.NOT_FOUND));

        List<Book> soleAuthoredBooks = author.getBooks().stream()
                .filter(b -> b.getAuthors().size() == 1)
                .toList();

        if (!soleAuthoredBooks.isEmpty()) {
            String titles = soleAuthoredBooks.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.joining(", "));
            throw new ServiceException(
                    "Cannot delete author: sole author of [" + titles + "]. Reassign or delete these books first.",
                    HttpStatus.CONFLICT
            );
        }

        author.getBooks().forEach(b -> b.getAuthors().remove(author));
        authorRepository.delete(author);
    }
}
