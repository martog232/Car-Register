package bg.infosys.interns.vregister.ws.dto.mapper;


import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Address;
import bg.infosys.interns.vregister.ws.dto.AddressDTO;

@Component
public class AddressMapper implements IModelMapper<AddressDTO, Address> {
	private final CityMapper cityMapper;
	
	public AddressMapper(CityMapper cityMapper) {
		this.cityMapper = cityMapper;
	}

	@Override
	public AddressDTO toDto(Address entity) {
		if (entity == null) return null;
		
		return new AddressDTO(entity.getId(), cityMapper.toDto(entity.getCity()), entity.getAddress(), entity.getIsValid());
	}

	@Override
	public Address toEntity(AddressDTO dto) {
		if(dto == null) return null;
		
		return new Address(dto.getId(), cityMapper.toEntity(dto.getCity()), dto.getAddress(), dto.getIsValid());
	}
	
	public Address toUpdatedAddress(Address address, AddressDTO addressDTO) {
		address.setAddress(addressDTO.getAddress());
		address.setCity(cityMapper.toEntity(addressDTO.getCity()));
		
		return address;
	}
}
