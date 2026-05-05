package com.library.cruddemo.rest;

import com.library.cruddemo.dto.DirectorDTO;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.service.interfaces.DirectorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Director", description = "Operations for directors")
public class DirectorRestController {

    private final DirectorService directorService;

    public DirectorRestController(DirectorService directorService) {
        this.directorService = directorService;
    }

    // ====== GET MAPPINGS ======

    @GetMapping("/directors")
    public List<DirectorDTO> getAllDirectors() {
        return directorService.findAll();
    }

    @GetMapping("/directors/{directorId}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable int directorId) throws ServiceException {
        return ResponseEntity.ok(directorService.getDirectorById(directorId));
    }

    @GetMapping("/libs/{libId}/directors")
    public ResponseEntity<DirectorDTO> getDirectorByLibId(@PathVariable int libId) throws ServiceException {
        return ResponseEntity.ok(directorService.getDirectorByLibId(libId));
    }

    // ====== POST MAPPINGS ======

    @PostMapping("/directors")
    public ResponseEntity<DirectorDTO> addDirector(@RequestBody DirectorDTO directorDTO) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(directorService.saveDirector(directorDTO));
    }

    @PostMapping("/libs/{libId}/directors/{directorId}")
    public ResponseEntity<String> assignDirectorToLib(@PathVariable("libId") int libId, @PathVariable("directorId") int directorId)
            throws ServiceException {
        directorService.assignDirectorToLib(libId, directorId);
        return ResponseEntity.ok("Director with id - " + directorId + " is now assigned to Library with id - " + libId);
    }

    // ====== DELETE MAPPINGS ======

    @DeleteMapping("/directors/{directorId}")
    public ResponseEntity<String> deleteDirectorById(@PathVariable int directorId) throws ServiceException {
        directorService.deleteDirectorById(directorId);
        return ResponseEntity.ok("Deleted Director with id - " + directorId);
    }

    @DeleteMapping("/libs/{libId}/directors")
    public ResponseEntity<String> unassignDirectorByLib(@PathVariable int libId) throws ServiceException {
        directorService.unassignDirectorByLibId(libId);
        return ResponseEntity.ok("Director deleted for Library with id - " + libId);
    }
}
