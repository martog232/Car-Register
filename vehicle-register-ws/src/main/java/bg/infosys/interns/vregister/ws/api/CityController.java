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
import bg.infosys.interns.vregister.ws.dto.CityDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "Cities")
@RequestMapping(ControllerConfig.CITIES_URL)
public class CityController {
	private final CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Cities", notes = "This method will return all Cities")
	public List<CityDTO> findAll() {
		return cityService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Cities", notes = "This method will return page of Cities")
	public Page<CityDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String countryName,
			@RequestParam(required = false) Boolean isValid) {
		return cityService.findAllByFilter(name, countryName, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one City", notes = "This method will return the City matching the passed id or HTTP Status 404 Not Found")
	public CityDTO findById(@PathVariable Integer id) {
		return cityService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new City", notes = "This method will create new City or HTTP Status 400 Bad Request")
	public CityDTO create(@RequestBody @Valid CityDTO cityDTO) {
		return cityService.save(cityDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update City", notes = "This method will update the City with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public CityDTO update(@PathVariable Integer id, @RequestBody @Valid CityDTO cityDTO) {
		return cityService.update(cityDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of City", notes = "This method will change the isValid of the city from true to false and vice versa.")
	public CityDTO changeIsValid(@PathVariable Integer id) {
		return cityService.changeIsValid(id);
	}
}
