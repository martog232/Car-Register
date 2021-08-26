package bg.infosys.interns.vregister.ws.dto.mapper;

import org.springframework.stereotype.Component;

import bg.infosys.interns.vregister.core.entity.FuelType;
import bg.infosys.interns.vregister.ws.dto.FuelTypeDTO;

@Component
public class FuelTypeMapper implements IModelMapper<FuelTypeDTO, FuelType> {

	@Override
	public FuelTypeDTO toDto(FuelType entity) {
		if (entity == null)
			return null;

		return new FuelTypeDTO(entity.getId(), entity.getName(), entity.getCode(), entity.getIsValid());
	}

	@Override
	public FuelType toEntity(FuelTypeDTO dto) {
		if (dto == null)
			return null;

		return new FuelType(dto.getId(), dto.getName(), dto.getCode(), dto.getIsValid());
	}

	public FuelType toUpdatedFuelType(FuelType fuelType, FuelTypeDTO fuelTypeDTO) {
		fuelType.setName(fuelTypeDTO.getName());
		fuelType.setCode(fuelTypeDTO.getCode());

		return fuelType;
	}
}
