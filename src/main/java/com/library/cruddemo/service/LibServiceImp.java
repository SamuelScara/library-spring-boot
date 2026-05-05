package com.library.cruddemo.service;

import com.library.cruddemo.dao.LibRepository;
import com.library.cruddemo.dto.LibDTO;
import com.library.cruddemo.entity.Book;
import com.library.cruddemo.entity.Lib;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.mapper.LibraryMapper;
import com.library.cruddemo.service.interfaces.LibService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibServiceImp implements LibService {

    private final LibRepository libRepository;
    private final LibraryMapper libraryMapper;

    public LibServiceImp(LibRepository libRepository, LibraryMapper libraryMapper) {
        this.libRepository = libRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LibDTO> findAll() {
        return libRepository.findAll().stream()
                .map(libraryMapper::toLibDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LibDTO getLibById(int libId) throws ServiceException {
        Lib lib = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Lib not found for id - " + libId, HttpStatus.NOT_FOUND));
        return libraryMapper.toLibDTO(lib);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = ServiceException.class)
    public LibDTO saveLib(LibDTO libDTO) throws ServiceException {
        Lib lib = libraryMapper.toLibEntity(libDTO);
        lib.setId(0);
        return libraryMapper.toLibDTO(libRepository.save(lib));
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteLibById(int libId) throws ServiceException {
        Lib toDelete = libRepository.findById(libId)
                .orElseThrow(() -> new ServiceException("Lib not found for id - " + libId, HttpStatus.NOT_FOUND));

        // Checking if the book is also in others libraries and setting availability false if not
        List<Book> books = toDelete.getBooks();
        books.forEach(book -> {
            boolean stillInOtherLibs = book.getLibraries().stream().anyMatch(lib -> lib.getId() != libId);
            if (!stillInOtherLibs) {
                book.setAvailability(false);
            }
        });
        
        libRepository.delete(toDelete);
    }
}
