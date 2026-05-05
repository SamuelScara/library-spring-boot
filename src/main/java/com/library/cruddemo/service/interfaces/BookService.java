package com.library.cruddemo.service.interfaces;

import com.library.cruddemo.dto.BookDTO;
import com.library.cruddemo.exception.ServiceException;

import java.util.List;

public interface BookService {

    List<BookDTO> findAll();

    BookDTO getBookById(int bookId) throws ServiceException;

    BookDTO saveBook(BookDTO bookDTO) throws ServiceException;

    BookDTO updateBook(int bookId, BookDTO bookDTO) throws ServiceException;

    void assignBookToLib(int bookId, int libId) throws ServiceException;

    void unassignBookFromLib(int bookId, int libId) throws ServiceException;

    void assignAuthorToBook(int authorId, int bookId) throws ServiceException;

    void unassignAuthorFromBook(int authorId, int bookId) throws ServiceException;

    void deleteBookById(int bookId) throws ServiceException;

}
