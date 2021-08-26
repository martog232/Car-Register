package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.CarTypeDAO;
import bg.infosys.interns.vregister.core.entity.CarType;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.CarTypeDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.CarTypeMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class CarTypeService {
	private final CarTypeDAO carTypeDAO;
	private final CarTypeMapper carTypeMapper;
	
	@Autowired
	public CarTypeService(CarTypeDAO carTypeDAO, CarTypeMapper carTypeMapper) {
		this.carTypeDAO = carTypeDAO;
		this.carTypeMapper = carTypeMapper;
	}
	
	/**
	 * Returns list of all car types. If there is no CarType in the database, returns an empty list.
	 * 
	 * @return list of all car types
	 */
	@Cacheable(value = "carTypes")
	public List<CarTypeDTO> findAll() {
		return carTypeDAO.findAll().stream()
						           .map(carTypeMapper::toDto)
						           .collect(Collectors.toList());
	}
	
	/**
	 * Returns CarType with specified id.
	 * 
	 * @param id id of the CarType to return
	 * @return the CarType with the specified id
	 * @throws EntitiNotFoundException if the CarType with the specified id does not exist.
	 */
	@Cacheable(value = "carTypes")
	public CarTypeDTO findById(Integer id) {
		return Optional.ofNullable(carTypeDAO.findById(id))
					  .map(carTypeMapper::toDto)
					  .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * Creates new CarType.
	 * 
	 * @param CarTypeDTO CarType to be created
	 * @return the CarType that is created
	 */
	@CacheEvict(value = "carTypes", allEntries = true)
	@CachePut(value = "carTypes")
	@Transactional
	public  CarTypeDTO save(CarTypeDTO carTypeDTO) {
		carTypeDTO.setIsValid(true);
		CarType newCarType = carTypeDAO.saveOrUpdate(carTypeMapper.toEntity(carTypeDTO));
		
		return carTypeMapper.toDto(newCarType);
	}

	/**
	 * Updates name and code of the CarType with the specified id.
	 * 
	 * @param CarTypeDTO CarTypeDTO with updated name and code
	 * @param id id of the CarType to update
	 * @return the updated CarType
	 * @throws EntitiNotFoundException if the CarType with the specified id does not exist.
	 */
	@CacheEvict(value = "carTypes", allEntries = true)
	@CachePut(value = "carTypes")
	@Transactional
	public CarTypeDTO update(CarTypeDTO carTypeDTO, Integer id) {
		CarType updatedCarType = Optional.ofNullable(carTypeDAO.findById(id))
			  .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedCarType = carTypeDAO.saveOrUpdate(carTypeMapper.toUpdatedCarType(updatedCarType, carTypeDTO));
		
		return carTypeMapper.toDto(updatedCarType);
	}
	
	/**
	 * Changes activity of the specified CarType.
	 *  
	 * If the CarType is valid the method changes the CarType to invalid.
	 * If the CarType is invalid the method changes the CarType to valid.
	 * 
	 * @param id id of the CarType to change activity
	 * @return the updated CarType
	 * @throws EntitiNotFoundException if the CarType with the specified id does not exist.
	 */
	@CacheEvict(value = "carTypes", allEntries = true)
	@CachePut(value = "carTypes")
	@Transactional
	public CarTypeDTO changeIsValid(Integer id) {
		CarType updatedCarType = Optional.ofNullable(carTypeDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedCarType.setIsValid(!updatedCarType.getIsValid());
		updatedCarType = carTypeDAO.saveOrUpdate(updatedCarType);
				
		return carTypeMapper.toDto(updatedCarType);
	}
}
