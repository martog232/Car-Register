package bg.infosys.interns.vregister.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.infosys.interns.vregister.core.dao.CityDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.CityDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.CityMapper;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class CityService {
	private final CityDAO cityDAO;
	private final CityMapper cityMapper;
	
	@Autowired
	public CityService(CityDAO cityDAO, CityMapper cityMapper) {
		this.cityDAO = cityDAO;
		this.cityMapper = cityMapper;
	}
	
	/**
	 * Returns list of all cities. If there is no city in the database, returns an empty list.
	 * 
	 * @return list of all cities
	 */
	public List<CityDTO> findAll() {
		return cityDAO.findAll().stream()
								.map(cityMapper::toDto)
								.collect(Collectors.toList());
	}
	
	/**
	 * 
	 * Returns page of cities. If there is no city in the database, returns an empty list.
	 * 
	 * @param name name of the city
	 * @param countryName name of the country where the city is located
	 * @param isValid Variable indicating whether the city is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of cities
	 */
	public Page<CityDTO> findAllByFilter(String name, String countryName, Boolean isValid, PagingSorting pagingSorting) {
		List<City> results = cityDAO.findAllByFilter(name, countryName, isValid, pagingSorting);
		
		return new Page<CityDTO>(results.stream()
				                        .map(cityMapper::toDto)
				                        .collect(Collectors.toList()), cityDAO.countAllByFilter(name, countryName, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * 
	 * Returns city with specified id.
	 * 
	 * @param id id of the city to return
	 * @return the city with the specified id
	 * @throws EntitiNotFoundException if the city with the specified id does not exist.
	 */
	public CityDTO findById(Integer id) {
		return Optional.ofNullable(cityDAO.findById(id))
				       .map(cityMapper::toDto)
				       .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * 
	 * Creates new City
	 * 
	 * @param cityDTO city to be created
	 * @return the city that is created
	 */
	@Transactional
	public  CityDTO save(CityDTO cityDTO) {
		cityDTO.setIsValid(true);
		City newCity = cityDAO.saveOrUpdate(cityMapper.toEntity(cityDTO));
		
		return cityMapper.toDto(newCity);
	}
	
	/**
	 * 
	 * Updates name and country of the city with the specified id.
	 * 
	 * @param cityDTO cityDTO with updated name and country
	 * @param id id of the city to update
	 * @return the updated city
	 * @throws EntitiNotFoundException if the city with the specified id does not exist.
	 */
	@Transactional
	public CityDTO update(CityDTO cityDTO, Integer id) {
		City updatedCity = Optional.ofNullable(cityDAO.findById(id))
				                   .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		updatedCity = cityDAO.saveOrUpdate(cityMapper.toUpdatedCity(updatedCity, cityDTO));
		
		return cityMapper.toDto(updatedCity);
	}
	
	/**
	 * 
	 * Changes isValid of the specified city.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the city to change isValid
	 * @return the updated city
	 * @throws EntitiNotFoundException if the city with the specified id does not exist.
	 */
	@Transactional
	public CityDTO changeIsValid(Integer id) {
		City updatedCity = Optional.ofNullable(cityDAO.findById(id))
								   .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedCity.setIsValid(!updatedCity.getIsValid());
		updatedCity = cityDAO.saveOrUpdate(updatedCity);
				
		return cityMapper.toDto(updatedCity);
	}
}
