package bg.infosys.interns.vregister.ws.dto.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Inspection;
import bg.infosys.interns.vregister.core.entity.ServiceStationService;
import bg.infosys.interns.vregister.ws.dto.InspectionDTO;
import bg.infosys.interns.vregister.ws.dto.ServiceStationServiceDTO;

@Component
public class InspectionMapper implements IModelMapper<InspectionDTO, Inspection> {
	private final VehicleMapper vehicleMapper;
	private final ServiceStationServiceMapper serviceStationServiceMapper;

	public InspectionMapper(VehicleMapper vehicleMapper, ServiceStationServiceMapper serviceStationServiceMapper) {
		this.vehicleMapper = vehicleMapper;
		this.serviceStationServiceMapper = serviceStationServiceMapper;
	}

	@Override
	public InspectionDTO toDto(Inspection entity) {
		if (entity == null) return null;
		
		List<ServiceStationServiceDTO> services = entity.getServiceStationServices().stream().map(serviceStationServiceMapper::toDto).collect(Collectors.toList());
		Set<ServiceStationServiceDTO> servicesSet = new HashSet<ServiceStationServiceDTO>(services);
		
		return new InspectionDTO(entity.getId(), vehicleMapper.toDto(entity.getVehicle()), servicesSet, entity.getDate().toString() ,entity.getTime().toString(), entity.getIsValid());
	}

	@Override
	public Inspection toEntity(InspectionDTO dto) {
		if(dto == null) return null;
		
		List<ServiceStationService> services = dto.getServiceStationServices().stream().map(serviceStationServiceMapper::toEntity).collect(Collectors.toList());
		Set<ServiceStationService> servicesSet = new HashSet<ServiceStationService>(services);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss");
		
		return new Inspection(dto.getId(), vehicleMapper.toEntity(dto.getVehicle()), servicesSet, LocalDate.parse(dto.getDate(), formatter), LocalTime.parse(dto.getTime(), timeFormatter), dto.getIsValid());
	}
	
	public Inspection toUpdatedInspection(Inspection inspection, InspectionDTO inspectionDTO) {
		List<ServiceStationService> services = inspectionDTO.getServiceStationServices().stream().map(serviceStationServiceMapper::toEntity).collect(Collectors.toList());
		Set<ServiceStationService> servicesSet = new HashSet<ServiceStationService>(services);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss");
		
		inspection.setServiceStationServices(servicesSet);
		inspection.setDate(LocalDate.parse(inspectionDTO.getDate(), formatter));
		inspection.setTime(LocalTime.parse(inspectionDTO.getTime(),timeFormatter));
		inspection.setVehicle(vehicleMapper.toEntity(inspectionDTO.getVehicle()));
		
		return inspection;
	}
}
