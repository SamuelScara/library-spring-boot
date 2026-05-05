package com.library.cruddemo.service.interfaces;

import com.library.cruddemo.dto.LibDTO;
import com.library.cruddemo.exception.ServiceException;

import java.util.List;

public interface LibService {

    List<LibDTO> findAll();

    LibDTO getLibById(int libId) throws ServiceException;

    LibDTO saveLib(LibDTO libDTO) throws ServiceException;

    void deleteLibById(int libId) throws ServiceException;

}
