package com.library.cruddemo.rest;

import com.library.cruddemo.dto.LibDTO;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.service.interfaces.LibService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Lib", description = "Operations on Lib")
public class LibRestController {

    private final LibService libService;

    public LibRestController(LibService libService) {
        this.libService = libService;
    }

    // ====== GET MAPPINGS ======

    @GetMapping("/libs")
    public List<LibDTO> getAllLibs() {
        return libService.findAll();
    }

    @GetMapping("/libs/{libId}")
    public ResponseEntity<LibDTO> getLibById(@PathVariable int libId) throws ServiceException {
        return ResponseEntity.ok(libService.getLibById(libId));
    }

    // ====== POST MAPPINGS ======

    @PostMapping("/libs")
    public ResponseEntity<LibDTO> addLib(@RequestBody LibDTO libDTO) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(libService.saveLib(libDTO));
    }
    
    // ====== PUT MAPPINGS ======

    @PutMapping("/libs/{libId}")
    public ResponseEntity<LibDTO> updateLib(@PathVariable int libId, @RequestBody LibDTO libDTO) throws ServiceException {
        return ResponseEntity.ok(libService.updateLib(libId, libDTO));
    }

    // ====== DELETE MAPPINGS ======

    @DeleteMapping("/libs/{libId}")
    public ResponseEntity<String> deleteLib(@PathVariable int libId) throws ServiceException {
        libService.deleteLibById(libId);
        return ResponseEntity.ok("Deleted Lib with id - " + libId);
    }
}
