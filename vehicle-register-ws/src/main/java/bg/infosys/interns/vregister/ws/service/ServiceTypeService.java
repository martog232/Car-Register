package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bg.infosys.interns.vregister.core.dao.ServiceTypeDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.ServiceType;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.ServiceTypeDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.ServiceTypeMapper;
//import bg.infosys.interns.vregister.ws.exception.DuplicateEntityException;

@Service
public class ServiceTypeService {
	private final ServiceTypeDAO serviceTypeDAO;
	private final ServiceTypeMapper serviceTypeMapper;

	@Autowired
	public ServiceTypeService(ServiceTypeDAO serviceTypeDAO, ServiceTypeMapper serviceTypeMapper) {
		this.serviceTypeDAO = serviceTypeDAO;
		this.serviceTypeMapper = serviceTypeMapper;
	}

	/**
	 * Returns all service types records
	 * 
	 * @return list of service types
	 */
	public List<ServiceTypeDTO> findAll() {
		return serviceTypeDAO.findAll().stream()
		           .map(serviceTypeMapper::toDto)
		           .collect(Collectors.toList());
		}
	
	/**
	 *  Returns page of ServiceTypes. If there is no serviceTypes in the database, returns an empty list.
	 * 
	 * @param name name of the service
	 * @param description description of service Type
	 * @param code code of the service type
	 * @param pagingSorting pagingSorting object
	 * @return page of service types
	 */
	public Page<ServiceTypeDTO> findAllByFilter(String name, String description, String code,Boolean isValid, PagingSorting pageSorting) {
		List<ServiceType> results = serviceTypeDAO.findAllByFilter(name, description, code,isValid, pageSorting);
		
		return new Page<ServiceTypeDTO>(results.stream()
				                           .map(serviceTypeMapper::toDto).collect(Collectors.toList()),
				                           serviceTypeDAO.countAllByFilter(name,description, code,isValid),
				                           pageSorting.getPageNumber(), pageSorting.getPageSize());
	}

	/**
	 * Returns the service type matching the passed id
	 * 
	 * @param serviceTypeId id of the service type
	 * @return ServiceType with the passed id
	 * @throws EntitiNotFoundException if the service type with the specified id
	 *                                 doesn't exists.
	 */
	public ServiceTypeDTO findById(Integer serviceTypeId) {
		return Optional.ofNullable(serviceTypeDAO.findById(serviceTypeId))
				.map(serviceTypeMapper::toDto)
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}

	/**
	 * Creates new ServiceType
	 * 
	 * @param serviceTypeDTO to be created
	 * @return created new serviceType
	 */
	@Transactional
	public ServiceTypeDTO save(ServiceTypeDTO serviceTypeDTO) {
		serviceTypeDTO.setIsValid(true);	
		
//		if(!isCodeUnique(serviceTypeDTO)) {
//			throw new DuplicateEntityException(APIErrorCode.DUPLICATE_ENTITY.getDescription());
//		}
		
		ServiceType serviceType=serviceTypeDAO.saveOrUpdate(serviceTypeMapper.toEntity(serviceTypeDTO));
		
		return serviceTypeMapper.toDto(serviceType);
	}

	/**
	 * Updates the ServiceType's name description and code with given id
	 * 
	 * @param serviceTypeDTO with updated parameters
	 * @param serviceTypeId  id of serviceType for update
	 * @return update serviceType
	 */
	@Transactional
	public ServiceTypeDTO update(ServiceTypeDTO serviceTypeDTO, Integer id) {
		ServiceType updatedServiceType = Optional.ofNullable(serviceTypeDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
//		if(!isCodeUnique(serviceTypeDTO) && serviceTypeDAO.findAllByCode(serviceTypeDTO.getCode()).get(0).getId() != id) {
//			throw new DuplicateEntityException(APIErrorCode.DUPLICATE_ENTITY.getDescription());
//		}
		
		updatedServiceType = serviceTypeDAO
				.saveOrUpdate(serviceTypeMapper.toUpdatedServiceType(updatedServiceType, serviceTypeDTO));
		
		return serviceTypeMapper.toDto(updatedServiceType);
	}

	/**
	 * Changes the value of the boolean variable isValid 
	 * 
	 * @param serviceTypeId id of serviceType to delete
	 * @throws EntitiNotFoundException if the serviceType with the specified id does not
	 *                                 exist.
	 */
	@Transactional
	public ServiceTypeDTO changeIsValid(Integer id) {
		ServiceType updatedServiceType = Optional.ofNullable(serviceTypeDAO.findById(id))
								   .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedServiceType.setIsValid(!updatedServiceType.getIsValid());
		updatedServiceType = serviceTypeDAO.saveOrUpdate(updatedServiceType);
				
		return serviceTypeMapper.toDto(updatedServiceType);
	}
	
	private Boolean isCodeUnique(ServiceTypeDTO serviceTypeDTO) {
		return serviceTypeDAO.findAllByCode(serviceTypeDTO.getCode()).size() == 0;
	}

}
