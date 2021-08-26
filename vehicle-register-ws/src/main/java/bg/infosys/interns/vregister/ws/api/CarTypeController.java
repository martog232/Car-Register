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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.infosys.interns.vregister.ws.config.ControllerConfig;
import bg.infosys.interns.vregister.ws.dto.CarTypeDTO;
import bg.infosys.interns.vregister.ws.service.CarTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "CarType")
@RequestMapping(ControllerConfig.CAR_TYPES_URL)
public class CarTypeController {
	private final CarTypeService carTypeService;

	@Autowired
	public CarTypeController(CarTypeService carTypeService) {
		this.carTypeService = carTypeService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Countries", notes = "This method will return all Countries")
	public List<CarTypeDTO> findAll() {
		return carTypeService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one CarType", notes = "This method will return the CarType matching the passed id or HTTP Status 404 Not Found")
	public CarTypeDTO findById(@PathVariable Integer id) {
		return carTypeService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new CarType", notes = "This method will create new CarType or HTTP Status 400 Bad Request")
	public CarTypeDTO create(@RequestBody @Valid CarTypeDTO carTypeDTO) {
		return carTypeService.save(carTypeDTO);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update CarType", notes = "This method will update the CarType with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public CarTypeDTO update(@PathVariable Integer id, @RequestBody @Valid CarTypeDTO carTypeDTO) {
		return carTypeService.update(carTypeDTO, id);
	}

	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change activity of CarType", notes = "This method will change the activity of the CarType from true to false and vice versa.")
	public CarTypeDTO changeIsValid(@PathVariable Integer id) {
		return carTypeService.changeIsValid(id);
	}
}
