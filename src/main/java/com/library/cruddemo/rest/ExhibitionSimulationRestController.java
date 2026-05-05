package com.library.cruddemo.rest;

import com.library.cruddemo.dto.ExhibitionSimulationDTO;
import com.library.cruddemo.dto.SimulationResponseDTO;
import com.library.cruddemo.exception.ServiceException;
import com.library.cruddemo.service.interfaces.ExhibitionSimulationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Exhibition Simulation", description = "Operations for Exhibition Simulations")
public class ExhibitionSimulationRestController {

    private final ExhibitionSimulationService exhibitionSimulationService;

    public ExhibitionSimulationRestController(ExhibitionSimulationService exhibitionSimulationService) {
        this.exhibitionSimulationService = exhibitionSimulationService;
    }

    // ====== GET MAPPINGS ======

    @GetMapping("/exhibition-simulations")
    public List<ExhibitionSimulationDTO> getAllExhibitionSimulations() throws ServiceException {
        return exhibitionSimulationService.findAll();
    }

    @GetMapping("/exhibition-simulations/{id}")
    public ResponseEntity<ExhibitionSimulationDTO> getExhibitionSimulationById(@PathVariable int id) throws ServiceException {
        return ResponseEntity.ok(exhibitionSimulationService.getExhibitionSimulationById(id));
    }

    // ====== POST MAPPINGS ======

    @PostMapping("/exhibition-simulations")
    public ResponseEntity<ExhibitionSimulationDTO> createExhibitionSimulation(
            @Valid @RequestBody ExhibitionSimulationDTO dto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(exhibitionSimulationService.saveExhibitionSimulation(dto));
    }

    @PostMapping("/exhibition-simulations/{id}/simulate")
    public ResponseEntity<SimulationResponseDTO> simulate(@PathVariable int id) throws ServiceException {
        return ResponseEntity.ok(exhibitionSimulationService.simulateExhibition(id));
    }
}
