package bg.infosys.interns.vregister.ws.dto.mapper;

import bg.infosys.interns.vregister.ws.dto.DriverDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Driver;

@Component
public class DriverMapper implements IModelMapper<DriverDTO, Driver> {
	private final AddressMapper addressMapper;

	@Autowired
	public DriverMapper(AddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}
	
    public DriverDTO toDto(Driver entity) {
        if (entity == null) return null;
  
        return new DriverDTO(entity.getId(), entity.getName(), addressMapper.toDto(entity.getAddress()), entity.getAge(), entity.getIsValid());
    }

	@Override
    public Driver toEntity(DriverDTO dto) {
        if (dto == null) return null;

        return new Driver(dto.getId(), dto.getName(), addressMapper.toEntity(dto.getAddress()), dto.getAge(), dto.getIsValid());
    }
	
	public Driver toUpdatedDriver(Driver driver, DriverDTO driverDTO) {
		driver.setName(driverDTO.getName());
		driver.setAge(driverDTO.getAge());
		driver.setAddress(addressMapper.toEntity(driverDTO.getAddress()));
		
		return driver;
	}
}
