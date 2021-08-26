package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.ServiceStationServiceDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.ServiceStationService;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.ServiceStationServiceDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.ServiceStationServiceMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class ServiceStationServiceService {
	private final ServiceStationServiceDAO serviceStationServiceDAO;
	private final ServiceStationServiceMapper serviceStationServiceMapper;
	
	@Autowired
	public ServiceStationServiceService(ServiceStationServiceDAO serviceStationServiceDAO, ServiceStationServiceMapper serviceStationServiceMapper) {
		this.serviceStationServiceDAO = serviceStationServiceDAO;
		this.serviceStationServiceMapper = serviceStationServiceMapper;
	}
	
	/**
	 * Returns list of all ServiceStationServices. If there is no ServiceStationService in the database, returns an empty list.
	 * 
	 * @return list of all ServiceStationService
	 */
	public List<ServiceStationServiceDTO> findAll() {
		return serviceStationServiceDAO.findAll().stream()
								     .map(serviceStationServiceMapper::toDto)
								     .collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of ServiceStationServices. If there is no ServiceStationService in the database, returns an empty list.
	 * 
	 * @param serviceStationName 	name of the ServiceStation
	 * @param serviceTypeName 		name of the service
	 * @param price 				price of the offered service
	 * @param hourDuration 			time to complete the service
	 * @param fuelType				fuel type service is supposed
	 * @param pagingSorting 		pagingSorting object
	 * @return page of ServiceStationServices
	 */
	public Page<ServiceStationServiceDTO> findAllByFilter(String serviceStationName, String serviceTypeName,String address,String cityName,String countryName,Integer price,Double hourDuration, String fuelTypeName, Boolean isValid,  PagingSorting pagingSorting) {
		List<ServiceStationService> results = serviceStationServiceDAO.findAllByFilter(serviceStationName, serviceTypeName,address,cityName,countryName,price,hourDuration,fuelTypeName, isValid, pagingSorting);
		
		return new Page<ServiceStationServiceDTO>(results.stream()
				                                       .map(serviceStationServiceMapper::toDto)
				                                       .collect(Collectors.toList()),serviceStationServiceDAO.countAllByFilter(serviceStationName, serviceTypeName,address,cityName,countryName,price,hourDuration,fuelTypeName, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns ServiceStationService with specified id.
	 * 
	 * @param id id of the ServiceStationService to return
	 * @return the ServiceStationService with the specified id
	 * @throws EntitiNotFoundException if the ServiceStationService with the specified id does not exist.
	 */
	public ServiceStationServiceDTO findById(Integer id) {
		return Optional.ofNullable(serviceStationServiceDAO.findById(id))
				       .map(serviceStationServiceMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new ServiceStationService
	 * 
	 * @param ServiceStationServiceDTO ServiceStationService to be created
	 * @return the ServiceStationService that is created
	 */
	@Transactional
	public  ServiceStationServiceDTO save(ServiceStationServiceDTO serviceStationServiceDTO) {
		serviceStationServiceDTO.setIsValid(true);
		ServiceStationService newServiceStationService = serviceStationServiceDAO.saveOrUpdate(serviceStationServiceMapper.toEntity(serviceStationServiceDTO));
		
		return serviceStationServiceMapper.toDto(newServiceStationService);
	}
	
	/**
	 * 
	 * Updates ServiceStation and Brand of the ServiceStationService with the specified id.
	 * 
	 * @param ServiceStationServiceDTO ServiceStationServiceDTO with updated serviceStation and brand
	 * @param id id of the ServiceStationService to update
	 * @return the updated ServiceStationService
	 * @throws EntitiNotFoundException if the ServiceStationService with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationServiceDTO update(ServiceStationServiceDTO serviceStationServiceDTO, Integer id) {
		ServiceStationService updatedServiceStationService = Optional.ofNullable(serviceStationServiceDAO.findById(id))
				                                                 .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedServiceStationService = serviceStationServiceDAO.saveOrUpdate(serviceStationServiceMapper.toUpdatedServiceStationService(updatedServiceStationService, serviceStationServiceDTO));
		
		return serviceStationServiceMapper.toDto(updatedServiceStationService);
	}
	
	/**
	 * 
	 * Changes isValid of the specified ServiceStationService.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the ServiceStationService to change isValid
	 * @return the updated ServiceStationService
	 * @throws EntitiNotFoundException if the ServiceStationService with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationServiceDTO changeIsValid(Integer id) {
		ServiceStationService updatedServiceStationService = Optional.ofNullable(serviceStationServiceDAO.findById(id))
								                                 .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedServiceStationService.setIsValid(!updatedServiceStationService.getIsValid());
		updatedServiceStationService = serviceStationServiceDAO.saveOrUpdate(updatedServiceStationService);
				
		return serviceStationServiceMapper.toDto(updatedServiceStationService);
	}
}
