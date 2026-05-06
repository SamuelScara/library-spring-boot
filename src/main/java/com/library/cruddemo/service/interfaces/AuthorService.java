package com.library.cruddemo.service.interfaces;

import com.library.cruddemo.dto.AuthorDTO;
import com.library.cruddemo.exception.ServiceException;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> findAll();

    AuthorDTO getAuthorById(int authorId) throws ServiceException;

    AuthorDTO saveAuthor(AuthorDTO authorDTO) throws ServiceException;

    AuthorDTO updateAuthor(int authorId, AuthorDTO authorDTO) throws ServiceException;

    void deleteAuthorById(int id) throws ServiceException;
}
