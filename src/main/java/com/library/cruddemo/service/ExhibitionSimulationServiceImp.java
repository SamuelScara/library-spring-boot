package com.library.cruddemo.service;

import com.library.cruddemo.dao.BookRepository;
import com.library.cruddemo.dao.ExhibitionSimulationRepository;
import com.library.cruddemo.dao.LibRepository;
import com.library.cruddemo.dto.ExhibitionSimulationDTO;
import com.library.cruddemo.dto.SimulationEntryDTO;
import com.library.cruddemo.dto.SimulationResponseDTO;
import com.library.cruddemo.entity.Book;
import com.library.cruddemo.entity.ExhibitionSimulation;
import com.library.cruddemo.entity.Lib;
import com.library.cruddemo.entity.SimulationEntry;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.mapper.LibraryMapper;
import com.library.cruddemo.service.interfaces.ExhibitionSimulationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ExhibitionSimulationServiceImp implements ExhibitionSimulationService {

    private final ExhibitionSimulationRepository exhibitionSimulationRepository;
    private final BookRepository bookRepository;
    private final LibRepository libRepository;
    private final LibraryMapper libraryMapper;

    public ExhibitionSimulationServiceImp(ExhibitionSimulationRepository exhibitionSimulationRepository,
                                          BookRepository bookRepository,
                                          LibRepository libRepository,
                                          LibraryMapper libraryMapper) {
        this.exhibitionSimulationRepository = exhibitionSimulationRepository;
        this.bookRepository = bookRepository;
        this.libRepository = libRepository;
        this.libraryMapper = libraryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExhibitionSimulationDTO> findAll() throws ServiceException {
        return exhibitionSimulationRepository.findAll().stream()
                .map(libraryMapper::toExhibitionSimulationDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ExhibitionSimulationDTO getExhibitionSimulationById(int id) throws ServiceException {
        ExhibitionSimulation simulation = exhibitionSimulationRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Exhibition simulation not found for id - " + id, HttpStatus.NOT_FOUND));
        return libraryMapper.toExhibitionSimulationDTO(simulation);
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public ExhibitionSimulationDTO saveExhibitionSimulation(ExhibitionSimulationDTO exhibitionSimulationDTO) throws ServiceException {

        if (exhibitionSimulationDTO.getBookIds() == null || exhibitionSimulationDTO.getBookIds().isEmpty()) {
            throw new ServiceException("At least one book is required", HttpStatus.BAD_REQUEST);
        }

        Lib lib = libRepository.findById(exhibitionSimulationDTO.getLibId())
                .orElseThrow(() -> new ServiceException("Library not found for id - " + exhibitionSimulationDTO.getLibId(), HttpStatus.NOT_FOUND));
        List<Book> books = bookRepository.findAllById(exhibitionSimulationDTO.getBookIds());

        ExhibitionSimulation simulation = libraryMapper.toExhibitionSimulationEntity(exhibitionSimulationDTO);
        simulation.setId(0);
        simulation.setLib(lib);

        List<SimulationEntry> entries = books.stream()
                .map(book -> new SimulationEntry(simulation, 0, 0, book))
                .collect(Collectors.toCollection(ArrayList::new));

        simulation.getEntries().addAll(entries);
        return libraryMapper.toExhibitionSimulationDTO(exhibitionSimulationRepository.save(simulation));
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public SimulationResponseDTO simulateExhibition(int id) throws ServiceException {
        ExhibitionSimulation simulation = exhibitionSimulationRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Exhibition simulation not found for id - " + id, HttpStatus.NOT_FOUND));

        List<SimulationEntry> entries = simulation.getEntries();
        if (entries.isEmpty()) {
            throw new ServiceException("No books found for this exhbition", HttpStatus.BAD_REQUEST);
        }

        Random random = new Random();
        entries.forEach(e -> e.setVisitorsNum(1000 + random.nextInt(9001)));
        entries.sort(Comparator.comparingInt(SimulationEntry::getVisitorsNum).reversed());
        for (int i = 0; i < entries.size(); i++) entries.get(i).setPosition(i + 1);

        simulation.setSimulationDate(LocalDate.now());
        exhibitionSimulationRepository.save(simulation);


        List<SimulationEntryDTO> ranking = entries.stream()
                .map(libraryMapper::toSimulationEntryDTO)
                .toList();

        return new SimulationResponseDTO(
                simulation.getId(),
                simulation.getTitle(),
                simulation.getLib().getName(),
                simulation.getSimulationDate(),
                ranking
        );
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public ExhibitionSimulationDTO updateExhibitionSimulation(int id, ExhibitionSimulationDTO dto) throws ServiceException {
        ExhibitionSimulation simulation = exhibitionSimulationRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Exhibition not found for id - " + id, HttpStatus.NOT_FOUND));

        Lib lib = libRepository.findById(dto.getLibId())
                .orElseThrow(() -> new ServiceException("Library not found for id - " + dto.getLibId(), HttpStatus.NOT_FOUND));

        simulation.setTitle(dto.getTitle());
        simulation.setStartDate(dto.getStartDate());
        simulation.setEndDate(dto.getEndDate());
        simulation.setSimulationDate(dto.getSimulationDate());
        simulation.setLib(lib);

        simulation.getEntries().clear();
        List<Book> books = bookRepository.findAllById(dto.getBookIds());
        books.forEach(book -> simulation.getEntries().add(new SimulationEntry(simulation, 0, 0, book)));

        return libraryMapper.toExhibitionSimulationDTO(exhibitionSimulationRepository.save(simulation));
    }

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void deleteExhibitionSimulation(int id) throws ServiceException {
        ExhibitionSimulation simulation = exhibitionSimulationRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Exhibition not found for id - " + id, HttpStatus.NOT_FOUND));
        exhibitionSimulationRepository.delete(simulation);
    }
}
