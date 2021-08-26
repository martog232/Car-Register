package bg.infosys.interns.vregister.ws.api;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import bg.infosys.interns.vregister.ws.dto.DriverDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.DriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "Drivers")
@RequestMapping(ControllerConfig.DRIVERS_URL)
public class DriverController {
	private final DriverService DriverService;

	@Autowired
	public DriverController(DriverService DriverService) {
		this.DriverService = DriverService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Drivers", notes = "This method will return all Drivers")
	public List<DriverDTO> findAll() {
		return DriverService.findAll();
	}

	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Drivers", notes = "This method will return page of Drivers")
	public Page<DriverDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String country,
			@RequestParam(required = false) Short age,
			@RequestParam(required = false) Boolean isValid) {
		return DriverService.findAllByFilter(name, address, city, country, age,isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Driver", notes = "This method will return the Driver matching the passed id or HTTP Status 404 Not Found")
	public DriverDTO findById(@PathVariable Integer id) {
		return DriverService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Driver", notes = "This method will create new Driver or HTTP Status 400 Bad Request")
	public DriverDTO create(@RequestBody @Valid DriverDTO DriverDTO) {
		return DriverService.save(DriverDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Driver", notes = "This method will update the Driver with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public DriverDTO update(@PathVariable Integer id, @RequestBody @Valid DriverDTO DriverDTO) {
		return DriverService.update(DriverDTO, id);
	}

	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Driver", notes = "This method change isValid of the Driver from true to false and vice versa.")
	public DriverDTO changeIsValid(@PathVariable Integer id) {
		return DriverService.changeIsValid(id);
	}
}
