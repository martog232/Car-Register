package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.ServiceStationBrand;
import bg.infosys.interns.vregister.ws.dto.ServiceStationBrandDTO;

@Component
public class ServiceStationBrandMapper implements IModelMapper<ServiceStationBrandDTO, ServiceStationBrand> {
	private final ServiceStationMapper serviceStationMapper;
	private final BrandMapper brandMapper;
	
	public ServiceStationBrandMapper(ServiceStationMapper serviceStationMapper, BrandMapper brandMapper) {
		this.serviceStationMapper = serviceStationMapper;
		this.brandMapper = brandMapper;
	}

	@Override
	public ServiceStationBrandDTO toDto(ServiceStationBrand entity) {
		if (entity == null) return null;
		
		return new ServiceStationBrandDTO(entity.getId(), serviceStationMapper.toDto(entity.getServiceStation()), brandMapper.toDto(entity.getBrand()), entity.getIsValid());
	}

	@Override
	public ServiceStationBrand toEntity(ServiceStationBrandDTO dto) {
		if(dto == null) return null;
		
		return new ServiceStationBrand(dto.getId(), serviceStationMapper.toEntity(dto.getServiceStation()), brandMapper.toEntity(dto.getBrand()), dto.getIsValid());
	}
	
	public ServiceStationBrand toUpdatedServiceStationBrand(ServiceStationBrand serviceStationBrand, ServiceStationBrandDTO serviceStationBrandDTO) {
		serviceStationBrand.setServiceStation(serviceStationMapper.toEntity(serviceStationBrandDTO.getServiceStation()));
		serviceStationBrand.setBrand(brandMapper.toEntity(serviceStationBrandDTO.getBrand()));
		
		return serviceStationBrand;
	}
}
