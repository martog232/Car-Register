package bg.infosys.interns.vregister.ws.dto.mapper;

import bg.infosys.interns.vregister.core.entity.ServiceType;

import org.springframework.stereotype.Component;
import bg.infosys.interns.vregister.ws.dto.ServiceTypeDTO;

@Component
public class ServiceTypeMapper implements IModelMapper<ServiceTypeDTO, ServiceType> {

	@Override
	public ServiceTypeDTO toDto(ServiceType entity) {
		if (entity == null) return null;

		return new ServiceTypeDTO(entity.getId(), entity.getName(), entity.getDescription(), entity.getCode(),entity.getIsValid());
	}
	
	@Override
	public ServiceType toEntity(ServiceTypeDTO dto) {
		if (dto == null) return null;

		return new ServiceType(dto.getId(), dto.getName(), dto.getDescription(), dto.getCode(), dto.getIsValid());
	}

	public ServiceType toUpdatedServiceType(ServiceType serviceType, ServiceTypeDTO serviceTypeDTO) {
		serviceType.setName(serviceTypeDTO.getName());
		serviceType.setDescription(serviceTypeDTO.getDescription());
		serviceType.setCode(serviceTypeDTO.getCode());
		return serviceType;
	}

}
