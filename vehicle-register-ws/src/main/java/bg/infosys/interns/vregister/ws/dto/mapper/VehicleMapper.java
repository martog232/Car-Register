package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.Vehicle;
import bg.infosys.interns.vregister.ws.dto.VehicleDTO;

@Component
public class VehicleMapper implements IModelMapper<VehicleDTO, Vehicle> {

	private final CarTypeMapper carTypeMapper;
	private final FuelTypeMapper fuelTypeMapper;
	private final CountryMapper countryMapper;
	private final DriverMapper driverMapper;
	private final BrandMapper brandMapper;
	
	public VehicleMapper(CarTypeMapper carTypeMapper, FuelTypeMapper fuelTypeMapper, CountryMapper countryMapper, DriverMapper driverMapper, BrandMapper brandMapper) {
		this.carTypeMapper = carTypeMapper;
		this.fuelTypeMapper = fuelTypeMapper;
		this.countryMapper = countryMapper;
		this.driverMapper = driverMapper;
		this.brandMapper = brandMapper;
	}

	@Override
	public VehicleDTO toDto(Vehicle entity) {
		if (entity == null) return null;
		
		return new VehicleDTO(entity.getId(), 
							  entity.getYear(), 
							  carTypeMapper.toDto(entity.getCarType()), 
							  entity.getSeats(), 
							  entity.getDoors(), 
							  entity.getColor(), 
							  entity.getPower(), 
							  fuelTypeMapper.toDto(entity.getFuelType()), 
							  countryMapper.toDto(entity.getCountry()), 
							  driverMapper.toDto(entity.getDriver()), 
							  brandMapper.toDto(entity.getBrand()), 
							  entity.getRegNumber(), 
							  entity.getIsValid());
	}

	@Override
	public Vehicle toEntity(VehicleDTO dto) {
		if(dto == null) return null;
		
		return new Vehicle(dto.getId(),
				           dto.getYear(),
				           carTypeMapper.toEntity(dto.getCarType()), 
				           dto.getSeats(),
				           dto.getDoors(),
				           dto.getColor(),
				           dto.getPower(),
				           fuelTypeMapper.toEntity(dto.getFuelType()),
				           countryMapper.toEntity(dto.getCountry()),
				           driverMapper.toEntity(dto.getDriver()),
				           brandMapper.toEntity(dto.getBrand()),
				           dto.getRegNumber(),
				           dto.getIsValid());
	}
	
	public Vehicle toUpdatedVehicle(Vehicle vehicle, VehicleDTO vehicleDTO) {
		vehicle.setYear(vehicleDTO.getYear());
		vehicle.setCarType(carTypeMapper.toEntity(vehicleDTO.getCarType()));
		vehicle.setSeats(vehicleDTO.getSeats());
		vehicle.setDoors(vehicleDTO.getDoors());
		vehicle.setColor(vehicleDTO.getColor());
		vehicle.setPower(vehicleDTO.getPower());
		vehicle.setFuelType(fuelTypeMapper.toEntity(vehicleDTO.getFuelType()));
		vehicle.setCountry(countryMapper.toEntity(vehicleDTO.getCountry()));
		vehicle.setDriver(driverMapper.toEntity(vehicleDTO.getDriver()));
		vehicle.setBrand(brandMapper.toEntity(vehicleDTO.getBrand()));
		vehicle.setRegNumber(vehicleDTO.getRegNumber());
		
		return vehicle;
	}
}
