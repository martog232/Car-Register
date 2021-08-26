package bg.infosys.interns.vregister.ws.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.ws.config.ControllerConfig;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.VehicleDTO;
import bg.infosys.interns.vregister.ws.service.VehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Vehicles")
@RequestMapping(ControllerConfig.VEHICLES_URL)
public class VehicleController {
	private final VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}
	 
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Vehicles", notes = "This method will return all Vehicles")
	public List<VehicleDTO> findAll() {
		return vehicleService.findAll();
	} 
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Vehicles", notes = "This method will return page of Vehicles")
	public Page<VehicleDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "year", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String carTypeName, 
			@RequestParam(required = false) Integer seats, 
			@RequestParam(required = false) Integer doors, 
			@RequestParam(required = false) String color, 
			@RequestParam(required = false) Integer power, 
			@RequestParam(required = false) String fuelTypeName, 
			@RequestParam(required = false) String countryName, 
			@RequestParam(required = false) String driverName, 
			@RequestParam(required = false) String brandName, 
			@RequestParam(required = false) String regNumber, 
			@RequestParam(required = false) Boolean isValid) {
		return vehicleService.findAllByFilter(year, carTypeName, seats, doors, color, power, fuelTypeName, countryName, 
				driverName, brandName, regNumber, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Vehicle", notes = "This method will return the Vehicle matching the passed id or HTTP Status 404 Not Found")
	public VehicleDTO findById(@PathVariable Integer id) {
		return vehicleService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Vehicle", notes = "This method will create new Vehicle or HTTP Status 400 Bad Request")
	public VehicleDTO create(@RequestBody @Valid VehicleDTO vehicleDTO) {
		return vehicleService.save(vehicleDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Vehicle", notes = "This method will update the Vehicle with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public VehicleDTO update(@PathVariable Integer id, @RequestBody @Valid VehicleDTO vehicleDTO) {
		return vehicleService.update(vehicleDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Vehicle", notes = "This method will change the isValid of the Vehicle from true to false and vice versa.")
	public VehicleDTO changeIsValid(@PathVariable Integer id) {
		return vehicleService.changeIsValid(id);
	}
}
