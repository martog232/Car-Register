package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.ws.dto.CityDTO;

@Component
public class CityMapper implements IModelMapper<CityDTO, City> {
	private final CountryMapper countryMapper;

	public CityMapper(CountryMapper countryMapper) {
		this.countryMapper = countryMapper;
	}

	@Override
	public CityDTO toDto(City entity) {
		if (entity == null) return null;
	
		return new CityDTO(entity.getId(), entity.getName(), countryMapper.toDto(entity.getCountry()), entity.getIsValid());
	}

	@Override
	public City toEntity(CityDTO dto) {
		if(dto == null) return null;
		
		return new City(dto.getId(), dto.getName(), countryMapper.toEntity(dto.getCountry()), dto.getIsValid());
	}
	
	public City toUpdatedCity(City city, CityDTO cityDTO) {
		city.setName(cityDTO.getName());
		city.setCountry(countryMapper.toEntity(cityDTO.getCountry()));
		
		return city;
	}
}
