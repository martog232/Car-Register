package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.InspectionDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Inspection;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.InspectionDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.InspectionMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class InspectionService {
	private final InspectionDAO inspectionDAO;
	private final InspectionMapper inspectionMapper;
	
	@Autowired
	public InspectionService(InspectionDAO inspectionDAO, InspectionMapper inspectionMapper) {
		this.inspectionDAO = inspectionDAO;
		this.inspectionMapper = inspectionMapper;
	}
	
	/**
	 * Returns list of all Inspections. If there is no Inspection in the database, returns an empty list.
	 * 
	 * @return list of all Inspections
	 */
	public List<InspectionDTO> findAll() {
		return inspectionDAO.findAll().stream()
								   .map(inspectionMapper::toDto)
								   .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of Inspections. If there is no Inspection in the database, returns an empty list.
	 * 
	 * @param vehicleRegNumber regNumber of vehicle
	 * @param date date of the Inspection
	 * @param isValid Variable indicating whether the vehicle is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of Inspections
	 */
	public Page<InspectionDTO> findAllByFilter(String vehicleRegNumber, String date,String time, Boolean isValid, PagingSorting pagingSorting) {
		List<Inspection> results = inspectionDAO.findAllByFilter(vehicleRegNumber, date,time, isValid, pagingSorting);
		
		return new Page<InspectionDTO>(results.stream()
				                           .map(inspectionMapper::toDto)
				                           .collect(Collectors.toList()), inspectionDAO.countAllByFilter(vehicleRegNumber, date,time, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns Inspection with specified id.
	 * 
	 * @param id id of the Inspection to return
	 * @return the Inspection with the specified id
	 * @throws EntitiNotFoundException if the Inspection with the specified id does not exist.
	 */
	public InspectionDTO findById(Integer id) {
		return Optional.ofNullable(inspectionDAO.findById(id))
				       .map(inspectionMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new Inspections
	 * 
	 * @param inspectionDTO Inspection to be created
	 * @return the Inspection that is created
	 */
	@Transactional
	public  InspectionDTO save(InspectionDTO inspectionDTO) {
		inspectionDTO.setIsValid(true);
		Inspection newInspection = inspectionDAO.saveOrUpdate(inspectionMapper.toEntity(inspectionDTO));
		
		return inspectionMapper.toDto(newInspection);
	}
	
	/**
	 * 
	 * Updates serviceStationServices, date and Vehicle of the Inspection with the specified id.
	 * 
	 * @param inspectionDTO inspectionDTO with updated parameters
	 * @param id id of the Inspection to update
	 * @return the updated Inspection
	 * @throws EntitiNotFoundException if the Inspection with the specified id does not exist.
	 */
	@Transactional
	public InspectionDTO update(InspectionDTO inspectionDTO, Integer id) {
		Inspection updatedInspection = Optional.ofNullable(inspectionDAO.findById(id))
				                               .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedInspection = inspectionDAO.saveOrUpdate(inspectionMapper.toUpdatedInspection(updatedInspection, inspectionDTO));
		
		return inspectionMapper.toDto(updatedInspection);
	}
	
	/**
	 * 
	 * Changes isValid of the specified Inspection.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the Inspection to change isValid
	 * @return the updated Inspection
	 * @throws EntitiNotFoundException if the Inspection with the specified id does not exist.
	 */
	@Transactional
	public InspectionDTO changeIsValid(Integer id) {
		Inspection updatedInspection = Optional.ofNullable(inspectionDAO.findById(id))
								               .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedInspection.setIsValid(!updatedInspection.getIsValid());
		updatedInspection = inspectionDAO.saveOrUpdate(updatedInspection);
				
		return inspectionMapper.toDto(updatedInspection);
	}
}
