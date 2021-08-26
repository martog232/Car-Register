package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import bg.infosys.interns.vregister.core.dao.FuelTypeDAO;
import bg.infosys.interns.vregister.core.entity.FuelType;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.FuelTypeDTO;
import bg.infosys.interns.vregister.ws.dto.mapper.FuelTypeMapper;

@Service
public class FuelTypeService {
	private final FuelTypeDAO fuelTypeDAO;
	private final FuelTypeMapper fuelTypeMapper;

	@Autowired
	public FuelTypeService(FuelTypeDAO fuelTypeDAO, FuelTypeMapper fuelTypeMapper) {
		this.fuelTypeDAO = fuelTypeDAO;
		this.fuelTypeMapper = fuelTypeMapper;
	}

	/**
	 * Returns all service types records
	 * 
	 * @return list of service types
	 */
	@Cacheable(value = "fuelTypes")
	public List<FuelTypeDTO> findAll() {
		return fuelTypeDAO.findAll().stream().map(s -> fuelTypeMapper.toDto(s)).collect(Collectors.toList());
	}

	/**
	 * Returns the service type matching the passed id
	 * 
	 * @param FuelTypeId id of the service type
	 * @return FuelType with the passed id
	 * @throws EntitiNotFoundException if the service type with the specified id
	 *                                 doesn't exists.
	 */
	@Cacheable(value = "fuelTypes")
	public FuelTypeDTO findById(Integer id) {
		return Optional.ofNullable(fuelTypeDAO.findById(id)).map(fuelTypeMapper::toDto)
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}

	/**
	 * Creates new FuelType
	 * 
	 * @param FuelTypeDTO to be created
	 * @return created new FuelType
	 */
	@CacheEvict(value = "fuelTypes", allEntries = true)
	@CachePut(value = "fuelTypes")
	@Transactional
	public FuelTypeDTO save(FuelTypeDTO fuelTypeDTO) {
		fuelTypeDTO.setIsValid(true);
		FuelType fuelType = fuelTypeDAO.saveOrUpdate(fuelTypeMapper.toEntity(fuelTypeDTO));

		return fuelTypeMapper.toDto(fuelType);
	}

	/**
	 * Updates the FuelType's name description and code with given id
	 * 
	 * @param FuelTypeDTO with updated parameters
	 * @param FuelTypeId  id of FuelType for update
	 * @return update FuelType
	 */
	@CacheEvict(value = "fuelTypes", allEntries = true)
	@CachePut(value = "fuelTypes")
	@Transactional
	public FuelTypeDTO update(FuelTypeDTO fuelTypeDTO, Integer id) {
		FuelType updatedFuelType = Optional.ofNullable(fuelTypeDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedFuelType = fuelTypeDAO.saveOrUpdate(fuelTypeMapper.toUpdatedFuelType(updatedFuelType, fuelTypeDTO));

		return fuelTypeMapper.toDto(updatedFuelType);
	}

	/**
	 * Changes the value of the boolean variable isValid
	 * 
	 * @param FuelTypeId id of FuelType to delete
	 * @throws EntitiNotFoundException if the FuelType with the specified id does
	 *                                 not exist.
	 */
	@CacheEvict(value = "fuelTypes", allEntries = true)
	@CachePut(value = "fuelTypes")
	@Transactional
	public FuelTypeDTO changeIsValid(Integer id) {
		FuelType changedFuelType = Optional.ofNullable(fuelTypeDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		changedFuelType.setIsValid(!changedFuelType.getIsValid());

		return fuelTypeMapper.toDto(fuelTypeDAO.saveOrUpdate(changedFuelType));
	}
}
