package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.ServiceStationBrandDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.ServiceStationBrand;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.ServiceStationBrandDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.ServiceStationBrandMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class ServiceStationBrandService {
	private final ServiceStationBrandDAO serviceStationBrandDAO;
	private final ServiceStationBrandMapper serviceStationBrandMapper;
	
	@Autowired
	public ServiceStationBrandService(ServiceStationBrandDAO serviceStationBrandDAO, ServiceStationBrandMapper serviceStationBrandMapper) {
		this.serviceStationBrandDAO = serviceStationBrandDAO;
		this.serviceStationBrandMapper = serviceStationBrandMapper;
	}
	
	/**
	 * Returns list of all ServiceStationBrands. If there is no ServiceStationBrand in the database, returns an empty list.
	 * 
	 * @return list of all ServiceStationBrand
	 */
	public List<ServiceStationBrandDTO> findAll() {
		return serviceStationBrandDAO.findAll().stream()
								     .map(serviceStationBrandMapper::toDto)
								     .collect(Collectors.toList());
	}
	
	/**
	 * Returns page of ServiceStationBrands. If there is no ServiceStationBrand in the database, returns an empty list.
	 * 
	 * @param serviceStationName name of the ServiceStation
	 * @param brandName name of the brand
	 * @param isValid Variable indicating whether the ServiceStationBrand is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of ServiceStationBrands
	 */
	public Page<ServiceStationBrandDTO> findAllByFilter(String serviceStationName, String brandName, Boolean isValid,  PagingSorting pagingSorting) {
		List<ServiceStationBrand> results = serviceStationBrandDAO.findAllByFilter(serviceStationName, brandName, isValid, pagingSorting);
		
		return new Page<ServiceStationBrandDTO>(results.stream()
				                                       .map(serviceStationBrandMapper::toDto)
				                                       .collect(Collectors.toList()), serviceStationBrandDAO.countAllByFilter(serviceStationName, brandName, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns ServiceStationBrand with specified id.
	 * 
	 * @param id id of the ServiceStationBrand to return
	 * @return the ServiceStationBrand with the specified id
	 * @throws EntitiNotFoundException if the ServiceStationBrand with the specified id does not exist.
	 */
	public ServiceStationBrandDTO findById(Integer id) {
		return Optional.ofNullable(serviceStationBrandDAO.findById(id))
				       .map(serviceStationBrandMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new ServiceStationBrand
	 * 
	 * @param serviceStationBrandDTO ServiceStationBrand to be created
	 * @return the ServiceStationBrand that is created
	 */
	@Transactional
	public  ServiceStationBrandDTO save(ServiceStationBrandDTO serviceStationBrandDTO) {
		serviceStationBrandDTO.setIsValid(true);
		ServiceStationBrand newServiceStationBrand = serviceStationBrandDAO.saveOrUpdate(serviceStationBrandMapper.toEntity(serviceStationBrandDTO));
		
		return serviceStationBrandMapper.toDto(newServiceStationBrand);
	}
	
	/**
	 * 
	 * Updates ServiceStation and Brand of the ServiceStationBrand with the specified id.
	 * 
	 * @param serviceStationBrandDTO serviceStationBrandDTO with updated serviceStation and brand
	 * @param id id of the ServiceStationBrand to update
	 * @return the updated ServiceStationBrand
	 * @throws EntitiNotFoundException if the ServiceStationBrand with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationBrandDTO update(ServiceStationBrandDTO serviceStationBrandDTO, Integer id) {
		ServiceStationBrand updatedServiceStationBrand = Optional.ofNullable(serviceStationBrandDAO.findById(id))
				                                                 .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedServiceStationBrand = serviceStationBrandDAO.saveOrUpdate(serviceStationBrandMapper.toUpdatedServiceStationBrand(updatedServiceStationBrand, serviceStationBrandDTO));
		
		return serviceStationBrandMapper.toDto(updatedServiceStationBrand);
	}
	
	/**
	 * 
	 * Changes isValid of the specified ServiceStationBrand.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the ServiceStationBrand to change isValid
	 * @return the updated ServiceStationBrand
	 * @throws EntitiNotFoundException if the ServiceStationBrand with the specified id does not exist.
	 */
	@Transactional
	public ServiceStationBrandDTO changeIsValid(Integer id) {
		ServiceStationBrand updatedServiceStationBrand = Optional.ofNullable(serviceStationBrandDAO.findById(id))
								                                 .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedServiceStationBrand.setIsValid(!updatedServiceStationBrand.getIsValid());
		updatedServiceStationBrand = serviceStationBrandDAO.saveOrUpdate(updatedServiceStationBrand);
				
		return serviceStationBrandMapper.toDto(updatedServiceStationBrand);
	}
}
