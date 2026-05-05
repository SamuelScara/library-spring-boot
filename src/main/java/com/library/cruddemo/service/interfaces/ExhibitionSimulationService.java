package com.library.cruddemo.service.interfaces;

import com.library.cruddemo.dto.ExhibitionSimulationDTO;
import com.library.cruddemo.dto.SimulationResponseDTO;
import com.library.cruddemo.exception.ServiceException;

import java.util.List;

public interface ExhibitionSimulationService {

    List<ExhibitionSimulationDTO> findAll() throws ServiceException;

    ExhibitionSimulationDTO getExhibitionSimulationById(int id) throws ServiceException;

    ExhibitionSimulationDTO saveExhibitionSimulation(ExhibitionSimulationDTO dto) throws ServiceException;

    SimulationResponseDTO simulateExhibition(int id) throws ServiceException;
}
