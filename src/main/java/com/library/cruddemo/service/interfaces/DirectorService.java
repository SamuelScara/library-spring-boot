package com.library.cruddemo.service.interfaces;

import com.library.cruddemo.dto.DirectorDTO;
import com.library.cruddemo.exception.ServiceException;

import java.util.List;

public interface DirectorService {

    List<DirectorDTO> findAll();

    DirectorDTO getDirectorById(int directorId) throws ServiceException;

    DirectorDTO saveDirector(DirectorDTO directorDTO) throws ServiceException;

    void deleteDirectorById(int directorId) throws ServiceException;

    DirectorDTO getDirectorByLibId(int libId) throws ServiceException;

    void assignDirectorToLib(int libId, int directorId) throws ServiceException;

    void unassignDirectorByLibId(int libId) throws ServiceException;

}
