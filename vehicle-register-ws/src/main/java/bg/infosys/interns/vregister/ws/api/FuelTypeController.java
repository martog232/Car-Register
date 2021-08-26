package bg.infosys.interns.vregister.ws.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import bg.infosys.interns.vregister.ws.config.ControllerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.infosys.interns.vregister.ws.dto.FuelTypeDTO;
import bg.infosys.interns.vregister.ws.service.FuelTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="FuelTypes")
@RequestMapping(ControllerConfig.FUEL_TYPES_URL)
public class FuelTypeController {
	private final FuelTypeService fuelTypeService;
	
	@Autowired 
	public FuelTypeController(FuelTypeService fuelTypeService) {
		this.fuelTypeService = fuelTypeService;
	}
	
	@GetMapping 
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all FuelTypes", notes = "This method will return all FuelTypes")
	public List<FuelTypeDTO> findAll() {
		
		return fuelTypeService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one FuelType", notes = "This method will return the FuelType matching the passed id or HTTP Status 404 Not Found")
	public FuelTypeDTO findById(@PathVariable Integer id) {
		
		return  fuelTypeService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new FuelType", notes = "This method will create new FuelType")
	public FuelTypeDTO create(@RequestBody @Valid FuelTypeDTO fuelType) {
		
		return  fuelTypeService.save(fuelType);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update FuelType", notes = "This method will update the FuelType with given id or will return HTTP Status 404 Not Found")
	public FuelTypeDTO update(@PathVariable Integer id, @RequestBody @Valid FuelTypeDTO fuelType) {
		
		return fuelTypeService.update(fuelType, id);
	}

	@PutMapping("/change-is-valid/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete FuelType", notes = "This method will change isValid in FuelType matching the passed id")
	public FuelTypeDTO delete(@PathVariable Integer id) {
		
		return fuelTypeService.changeIsValid(id);
	}
}
