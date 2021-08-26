package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.DriverDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Driver;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.DriverDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.DriverMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class DriverService {
	private final DriverDAO driverDAO;
	private final DriverMapper driverMapper;

	public DriverService(DriverDAO driverDAO, DriverMapper driverMapper) {
		this.driverDAO = driverDAO;
		this.driverMapper = driverMapper;
	}

	/**
	 * Returns list of all Drivers. If there is no Driver in the database, returns
	 * an empty list.
	 * 
	 * @return list of all Drivers
	 */
	public List<DriverDTO> findAll() {
		return driverDAO.findAll().stream().map(driverMapper::toDto).collect(Collectors.toList());
	}

	/**
	 * 
	 * Returns page of Drivers. If there is no Driver in the database, returns an
	 * empty list.
	 * 
	 * @param driver        Driver
	 * @param address       Where the Driver lives
	 * @param age           Driver's age
	 * @param city          City, where driver lives
	 * @param country		Country,where the city is located
	 * @param isValid		Is Valid Driver's record
	 * @param paging		Sorting pagingSorting object
	 * @return				page of Drivers
	 */
	public Page<DriverDTO> findAllByFilter(String name, String address, String city, String country, Short age,
			Boolean isValid, PagingSorting pagingSorting) {
		List<Driver> results = driverDAO.findAllByFilter(name, address, city, country, age, isValid, pagingSorting);

		return new Page<DriverDTO>(results.stream().map(driverMapper::toDto).collect(Collectors.toList()),
				driverDAO.countAllByFilter(name,  address, city, country, age, isValid), pagingSorting.getPageNumber(),
				pagingSorting.getPageSize());
	}

	/**
	 * 
	 * Returns Driver with specified id.
	 * 
	 * @param id id of the Driver to return
	 * @return the Driver with the specified id
	 * @throws EntitiNotFoundException if the Driver with the specified id does not
	 *                                 exist.
	 */
	public DriverDTO findById(Integer id) {
		return Optional.ofNullable(driverDAO.findById(id)).map(driverMapper::toDto)
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}

	/**
	 * 
	 * Creates new Driver
	 * 
	 * @param DriverDTO Driver to be created
	 * @return the Driver that is created
	 */
	@Transactional
	public DriverDTO save(DriverDTO driverDTO) {
		driverDTO.setIsValid(true);
		Driver newDriver = driverDAO.saveOrUpdate(driverMapper.toEntity(driverDTO));

		return driverMapper.toDto(newDriver);
	}

	/**
	 * 
	 * Updates Driver(details) and address of the Driver with the specified id.
	 * 
	 * @param DriverDTO DriverDTO with updated Driver and Address
	 * @param id id of the Driver to update
	 * @return the updated Driver
	 * @throws EntitiNotFoundException if the Driver with the specified id does not exist.
	 */
	@Transactional
	public DriverDTO update(DriverDTO driverDTO, Integer id) {
		Driver updatedDriver = Optional.ofNullable(driverDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedDriver = driverDAO.saveOrUpdate(driverMapper.toUpdatedDriver(updatedDriver, driverDTO));

		return driverMapper.toDto(updatedDriver);
	}

	/**
	 * 
	 * Changes isValid of the specified Driver.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the Driver to change isValid
	 * @return Updated Driver
	 * @throws EntitiNotFoundException if the Driver with the specified id does not
	 *                                 exist.
	 */
	@Transactional
	public DriverDTO changeIsValid(Integer id) {
		Driver updatedDriver = Optional.ofNullable(driverDAO.findById(id))
				.orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));

		updatedDriver.setIsValid(!updatedDriver.getIsValid());
		updatedDriver = driverDAO.saveOrUpdate(updatedDriver);

		return driverMapper.toDto(updatedDriver);
	}
}
