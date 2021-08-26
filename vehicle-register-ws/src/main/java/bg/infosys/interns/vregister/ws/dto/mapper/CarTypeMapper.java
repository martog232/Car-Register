package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.CarType;
import bg.infosys.interns.vregister.ws.dto.CarTypeDTO;

@Component
public class CarTypeMapper implements IModelMapper<CarTypeDTO, CarType>{

	@Override
	public CarTypeDTO toDto(CarType entity) {
		if (entity == null) return null;
		
		return new CarTypeDTO(entity.getId(), entity.getName(), entity.getCode(), entity.getIsValid());
	}

	@Override
	public CarType toEntity(CarTypeDTO dto) {
		if(dto == null) return null;
		
		return new CarType(dto.getId(), dto.getName(), dto.getCode(), dto.getIsValid());
	}
	
	public CarType toUpdatedCarType(CarType carType, CarTypeDTO carTypeDTO) {
		carType.setName(carTypeDTO.getName());
		carType.setCode(carTypeDTO.getCode());
		
		return carType;
	}
}
