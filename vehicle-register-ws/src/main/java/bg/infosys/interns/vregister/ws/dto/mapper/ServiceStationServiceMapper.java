package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.ServiceStationService;
import bg.infosys.interns.vregister.ws.dto.ServiceStationServiceDTO;

@Component
public class ServiceStationServiceMapper implements IModelMapper<ServiceStationServiceDTO, ServiceStationService>{
	private final ServiceStationMapper serviceStationMapper;
	private final ServiceTypeMapper serviceTypeMapper;
	private final FuelTypeMapper fuelTypeMapper;

	public ServiceStationServiceMapper(ServiceStationMapper serviceStationMapper,ServiceTypeMapper serviceTypeMapper,FuelTypeMapper fuelTypeMapper) {
		this.serviceStationMapper = serviceStationMapper;
		this.serviceTypeMapper=serviceTypeMapper;
		this.fuelTypeMapper=fuelTypeMapper;
	}

	@Override
	public ServiceStationServiceDTO toDto(ServiceStationService entity) {
		if (entity == null) return null;
		
		return new ServiceStationServiceDTO(entity.getId(), serviceStationMapper.toDto(entity.getServiceStation()),
				serviceTypeMapper.toDto(entity.getServiceType()), entity.getPrice(),entity.getHourDuration(),
				fuelTypeMapper.toDto(entity.getFuelType()), entity.getIsValid());
	}

	@Override
	public ServiceStationService toEntity(ServiceStationServiceDTO dto) {
		if(dto == null) return null;
		
		return new ServiceStationService(dto.getId(), serviceStationMapper.toEntity(dto.getServiceStation()),
				serviceTypeMapper.toEntity(dto.getServiceType()), dto.getPrice(),dto.getHourDuration(),
				fuelTypeMapper.toEntity(dto.getFuelType()),dto.getIsValid());
	}
	
	public ServiceStationService toUpdatedServiceStationService(ServiceStationService serviceStationService, ServiceStationServiceDTO serviceStationServiceDTO) {
		serviceStationService.setServiceStation(serviceStationMapper.toEntity(serviceStationServiceDTO.getServiceStation()));
		serviceStationService.setServiceType(serviceTypeMapper.toEntity(serviceStationServiceDTO.getServiceType()));
		serviceStationService.setFuelType(fuelTypeMapper.toEntity(serviceStationServiceDTO.getFuelType()));
		
		return serviceStationService;
	}
}
