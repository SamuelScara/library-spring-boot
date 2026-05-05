package com.library.cruddemo.service;

import com.library.cruddemo.dao.DirectorRepository;
import com.library.cruddemo.dao.LibRepository;
import com.library.cruddemo.dto.DirectorDTO;
import com.library.cruddemo.entity.Director;
import com.library.cruddemo.entity.Lib;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.mapper.LibraryMapper;
import com.library.cruddemo.service.interfaces.DirectorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DirectorServiceImp implements DirectorService {

    private final DirectorRepository directorRepository;
    private final LibRepository libRepository;
    private final LibraryMapper libraryMapper;

    public DirectorServiceImp(DirectorRepository directorRepository, LibRepository libRepository, LibraryMapper libraryMapper) {
        this.directorRepository = directorRepository;
        this.libRepository = libRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectorDTO> findAll() {
        return directorRepository.findAll().stream()
                .map(libraryMapper::toDirectorDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DirectorDTO getDirectorById(int directorId) throws ServiceException {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ServiceException("Director with id - " + directorId + " not found", HttpStatus.NOT_FOUND));
        return libraryMapper.toDirectorDTO(director);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public DirectorDTO saveDirector(DirectorDTO directorDTO) throws ServiceException {
        Director director = libraryMapper.toDirectorEntity(directorDTO);
        director.setId(0);
        return libraryMapper.toDirectorDTO(directorRepository.save(director));
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteDirectorById(int directorId) throws ServiceException {
        Director toDelete = directorRepository.findById(directorId)
                .orElseThrow(() -> new ServiceException("Director with id - " + directorId + " not found", HttpStatus.NOT_FOUND));
        directorRepository.delete(toDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public DirectorDTO getDirectorByLibId(int libId) throws ServiceException {
        Lib lib = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Lib with id - " + libId + " not found", HttpStatus.NOT_FOUND));
        return libraryMapper.toDirectorDTO(lib.getDirector());
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void assignDirectorToLib(int libId, int directorId) throws ServiceException {
        Lib lib = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Lib with id - " + libId + " not found", HttpStatus.NOT_FOUND));

        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ServiceException("Director with id - " + directorId + " not found", HttpStatus.NOT_FOUND));

        if (lib.getDirector() != null) {
            throw new ServiceException("The library with id - " + libId + " already has a Director", HttpStatus.CONFLICT);
        }

        if (director.getLibrary() != null) {
            throw new ServiceException("The director with id - " + directorId + " is already assigned to a Library", HttpStatus.CONFLICT);
        }

        director.setLibrary(lib);
        directorRepository.save(director);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void unassignDirectorByLibId(int libId) throws ServiceException {
        Lib lib = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Lib with id - " + libId + " not found", HttpStatus.NOT_FOUND));

        Director director = lib.getDirector();

        if (director == null) {
            throw new ServiceException("The library with id - " + libId + " doesn't have a Director", HttpStatus.CONFLICT);
        }

        director.setLibrary(null);
        directorRepository.save(director);
    }
}
