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

import bg.infosys.interns.vregister.core.dao.CountryDAO;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.ws.api.error.APIErrorCode;
import bg.infosys.interns.vregister.ws.dto.CountryDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.mapper.CountryMapper;
import bg.infosys.interns.vregister.ws.exception.DuplicateEntityException;
import bg.infosys.interns.vregister.ws.exception.EntityNotFoundException;

@Service
public class CountryService {
	private final CountryDAO countryDAO;
	private final CountryMapper countryMapper;
	
	@Autowired
	public CountryService(CountryDAO countryDAO, CountryMapper countryMapper) {
		this.countryDAO = countryDAO;
		this.countryMapper = countryMapper;
	}
	
	/**
	 * Returns list of all countries. If there is no country in the database, returns an empty list.
	 * 
	 * @return list of all countries
	 */
	@Cacheable(value = "countries")
	public List<CountryDTO> findAll() {
		return countryDAO.findAll().stream()
						           .map(countryMapper::toDto)
						           .collect(Collectors.toList());
	}
	
	/**
	 *  Returns page of countries. If there is no country in the database, returns an empty list.
	 * 
	 * @param name name of the country
	 * @param code Two-letter country code defined in ISO 3166-1
	 * @param isValid Variable indicating whether the country is valid
	 * @param pagingSorting pagingSorting object
	 * @return page of countries
	 */
	public Page<CountryDTO> findAllByFilter(String name, String code, Boolean isValid, PagingSorting pagingSorting) {
		List<Country> results = countryDAO.findAllByFilter(name, code, isValid, pagingSorting);
		
		return new Page<CountryDTO>(results.stream()
				                           .map(countryMapper::toDto)
				                           .collect(Collectors.toList()), countryDAO.countAllByFilter(name, code, isValid), pagingSorting.getPageNumber(), pagingSorting.getPageSize());
	}
	
	/**
	 * Returns country with specified id.
	 * 
	 * @param id id of the country to return
	 * @return the country with the specified id
	 * @throws EntitiNotFoundException if the country with the specified id does not exist.
	 */
	@Cacheable(value = "countries")
	public CountryDTO findById(Integer id) {
		return Optional.ofNullable(countryDAO.findById(id))
					   .map(countryMapper::toDto)
					   .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
	}
	
	/**
	 * Creates new country.
	 * 
	 * @param countryDTO country to be created
	 * @return the country that is created
	 */
	@CacheEvict(value = "countries", allEntries = true)
	@CachePut(value = "countries")
	@Transactional
	public  CountryDTO save(CountryDTO countryDTO) {
		if(!isCodeUnique(countryDTO)) {
			throw new DuplicateEntityException(APIErrorCode.DUPLICATE_ENTITY.getDescription());
		}
		countryDTO.setIsValid(true);
		Country newCountry = countryDAO.saveOrUpdate(countryMapper.toEntity(countryDTO));
		return countryMapper.toDto(newCountry);
	}

	/**
	 * Updates name and code of the country with the specified id.
	 * 
	 * @param countryDTO countryDTO with updated name and code
	 * @param id id of the country to update
	 * @return the updated country
	 * @throws EntitiNotFoundException if the country with the specified id does not exist.
	 */
	@CacheEvict(value = "countries", allEntries = true)
	@CachePut(value = "countries")
	@Transactional
	public CountryDTO update(CountryDTO countryDTO, Integer id) {
		Country updatedCountry = Optional.ofNullable(countryDAO.findById(id))
				                         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		if(!isCodeUnique(countryDTO) && countryDAO.findAllByCode(countryDTO.getCode()).get(0).getId() != id) {
			throw new DuplicateEntityException(APIErrorCode.DUPLICATE_ENTITY.getDescription());
		}
		updatedCountry = countryDAO.saveOrUpdate(countryMapper.toUpdatedCountry(updatedCountry, countryDTO));
		return countryMapper.toDto(updatedCountry);
	}
	
	/**
	 * 
	 * Changes isValid of the specified country.
	 * 
	 * If the isValid is true the method changes it to false and vice versa.
	 * 
	 * @param id id of the country to change isValid
	 * @return the updated country
	 * @throws EntitiNotFoundException if the country with the specified id does not exist.
	 */
	@CacheEvict(value = "countries", allEntries = true)
	@CachePut(value = "countries")
	@Transactional
	public CountryDTO changeIsValid(Integer id) {
		Country updatedCountry = Optional.ofNullable(countryDAO.findById(id))
								         .orElseThrow(() -> new EntityNotFoundException(APIErrorCode.ENTITY_NOT_FOUND.getDescription()));
		
		updatedCountry.setIsValid(!updatedCountry.getIsValid());
		updatedCountry = countryDAO.saveOrUpdate(updatedCountry);
		
		return countryMapper.toDto(updatedCountry);
	}
	
	private Boolean isCodeUnique(CountryDTO countryDTO) {
		return countryDAO.findAllByCode(countryDTO.getCode()).size() == 0;
	}
}
