package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.ServiceStationDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.ServiceStationDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.ServiceStationMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class ServiceStationService {
	private final ServiceStationDAO serviceStationDAO;
	private final ServiceStationMapper serviceStationMapper;
	
	public ServiceStationService(ServiceStationDAO serviceStationDAO, ServiceStationMapper serviceStationMapper) {
		this.serviceStationDAO = serviceStationDAO;
		this.serviceStationMapper = serviceStationMapper;
	}
	
	/**
	 * Returns list of all ServiceStations. If there is no ServiceStation in the database, returns an empty list.
	 * 
	 * @return list of all ServiceStations
	 */
	public List<ServiceStationDTO> findAll() {
		return serviceStationDAO.findAll().stream()
								   .map(serviceStationMapper::toDto)
								   .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of ServiceStations. If there is no ServiceStation in the database, returns an empty list.
	 * 
	 * @param name name of the ServiceStation
	 * @param addressAddress address where the ServiceStation is located
	 * @param isValid Variable indicating whether the ServiceStation is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of ServiceStations
	 */
	public Page<ServiceStationDTO> findAllByFilter(String name, String addressAddress, Boolean isValid,Integer startWorkTime,Integer offWorkTime, PagingSorting pagingSorting) {
		List<ServiceStation> results = serviceStationDAO.findAllByFilter(name, addressAddress, isValid, startWorkTime,offWorkTime, pagingSorting);
		
		return new Page<ServiceStationDTO>(results.stream()
				                           .map(serviceStationMapper::toDto)
				                           .collect(Collectors.toList()), serviceStationDAO.countAllByFilter(name, addressAddress, isValid,startWorkTime,offWorkTime), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns ServiceStation with specified id.
	 * 
	 * @param id id of the ServiceStation to return
	 * @return the service station with the specified id
	 * @throws EntitiNotFoundException if the service station with the specified id does not exist.
	 */
	public ServiceStationDTO findById(Integer id) {
		return Optional.ofNullable(serviceStationDAO.findById(id))
				       .map(serviceStationMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new ServiceStation
	 * 
	 * @param serviceStationDTO serviceStation to be created
	 * @return the serviceStation that is created
	 */
	@Transactional
	public  ServiceStationDTO save(ServiceStationDTO serviceStationDTO) {
		serviceStationDTO.setIsValid(true);
		ServiceStation newServiceStation = serviceStationDAO.saveOrUpdate(serviceStationMapper.toEntity(serviceStationDTO));
		
		return serviceStationMapper.toDto(newServiceStation);
	}
	
	/**
	 * 
	 * Updates name and address of the ServiceStation with the specified id.
	 * 
	 * @param serviceStationDTO erviceStationDTO with updated name and address
	 * @param id id of the ServiceStation to update
	 * @return the updated ServiceStation
	 * @throws EntitiNotFoundException if the ServiceStation with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationDTO update(ServiceStationDTO ServiceStationDTO, Integer id) {
		ServiceStation updatedServiceStation = Optional.ofNullable(serviceStationDAO.findById(id))
				                         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedServiceStation = serviceStationDAO.saveOrUpdate(serviceStationMapper.toUpdatedServiceStation(updatedServiceStation, ServiceStationDTO));
		
		return serviceStationMapper.toDto(updatedServiceStation);
	}
	
	/**
	 * 
	 * Changes isValid of the specified ServiceStation.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the ServiceStation to change isValid
	 * @return the updated ServiceStation
	 * @throws EntitiNotFoundException if the ServiceStation with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationDTO changeIsValid(Integer id) {
		ServiceStation updatedServiceStation = Optional.ofNullable(serviceStationDAO.findById(id))
								         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedServiceStation.setIsValid(!updatedServiceStation.getIsValid());
		updatedServiceStation = serviceStationDAO.saveOrUpdate(updatedServiceStation);
				
		return serviceStationMapper.toDto(updatedServiceStation);
	}
}
