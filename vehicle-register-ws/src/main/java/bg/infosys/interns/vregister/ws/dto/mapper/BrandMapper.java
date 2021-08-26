package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Brand;
import bg.infosys.interns.vregister.ws.dto.BrandDTO;

@Component
public class BrandMapper implements IModelMapper<BrandDTO, Brand>{
	private final CountryMapper countryMapper;
	
	public BrandMapper(CountryMapper countryMapper) {
		this.countryMapper=countryMapper;
	}

	@Override
	public BrandDTO toDto(Brand entity) {
		if (entity == null) return null;
		
		return new BrandDTO(entity.getId(), entity.getName(), countryMapper.toDto(entity.getCountry()),entity.getIsValid());
	}

	@Override
	public Brand toEntity(BrandDTO dto) {
		if(dto == null) return null;
		
		return new Brand(dto.getId(), dto.getName(), countryMapper.toEntity(dto.getCountry()),dto.getIsValid());
	}
	
	public Brand toUpdatedBrand(Brand brand, BrandDTO brandDTO) {
		brand.setName(brandDTO.getName());
		brand.setCountry(countryMapper.toEntity(brandDTO.getCountry()));
		
		return brand;
	}
}