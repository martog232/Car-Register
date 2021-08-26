package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.ws.dto.CountryDTO;

@Component
public class CountryMapper implements IModelMapper<CountryDTO, Country>{

	@Override
	public CountryDTO toDto(Country entity) {
		if (entity == null) return null;
		
		return new CountryDTO(entity.getId(), entity.getName(), entity.getCode(), entity.getIsValid());
	}

	@Override
	public Country toEntity(CountryDTO dto) {
		if(dto == null) return null;
		
		return new Country(dto.getId(), dto.getName(), dto.getCode(), dto.getIsValid());
	}
	
	public Country toUpdatedCountry(Country country, CountryDTO countryDTO) {
		country.setName(countryDTO.getName());
		country.setCode(countryDTO.getCode());
		
		return country;
	}
}
