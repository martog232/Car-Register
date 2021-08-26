package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.ws.dto.ServiceStationDTO;

@Component
public class ServiceStationMapper implements IModelMapper<ServiceStationDTO, ServiceStation>{
	private final AddressMapper addressMapper;

	public ServiceStationMapper(AddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}

	@Override
	public ServiceStationDTO toDto(ServiceStation entity) {
		if (entity == null) return null;
		
		return new ServiceStationDTO(entity.getId(), entity.getName(), addressMapper.toDto(entity.getAddress()), entity.getIsValid(), entity.getStartWorkTime(),entity.getOffWorkTime());
	}

	@Override
	public ServiceStation toEntity(ServiceStationDTO dto) {
		if(dto == null) return null;
		
		return new ServiceStation(dto.getId(), dto.getName(), addressMapper.toEntity(dto.getAddress()), dto.getIsValid(),dto.getStartWorkTime(),dto.getOffWorkTime());
	}
	
	public ServiceStation toUpdatedServiceStation(ServiceStation serviceStation, ServiceStationDTO serviceStationDTO) {
		serviceStation.setName(serviceStationDTO.getName());
		serviceStation.setAddress(addressMapper.toEntity(serviceStationDTO.getAddress()));
		serviceStation.setStartWorkTime(serviceStationDTO.getStartWorkTime());
		serviceStation.setOffWorkTime(serviceStationDTO.getOffWorkTime());
		return serviceStation;
	}
}
