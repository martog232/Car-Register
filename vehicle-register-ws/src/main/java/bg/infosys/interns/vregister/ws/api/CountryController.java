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
import bg.infosys.interns.vregister.ws.dto.CountryDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.CountryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "Countires")
@RequestMapping(ControllerConfig.COUNTRIES_URL)
public class CountryController {
	private final CountryService countryService;

	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Countries", notes = "This method will return all Countries")
	public List<CountryDTO> findAll() {
		return countryService.findAll();
	}

	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Countries", notes = "This method will return page of Countries")
	public Page<CountryDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) Boolean isValid) {
		return countryService.findAllByFilter(name, code, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Country", notes = "This method will return the Country matching the passed id or HTTP Status 404 Not Found")
	public CountryDTO findById(@PathVariable Integer id) {
		return countryService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Country", notes = "This method will create new Country or HTTP Status 400 Bad Request")
	public CountryDTO create(@RequestBody @Valid CountryDTO countryDTO) {
		return countryService.save(countryDTO);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Country", notes = "This method will update the Country with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public CountryDTO update(@PathVariable Integer id, @RequestBody @Valid CountryDTO countryDTO) {
		return countryService.update(countryDTO, id);
	}

	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Country", notes = "This method will change the isValid of the country from true to false and vice versa.")
	public CountryDTO changeIsValid(@PathVariable Integer id) {
		return countryService.changeIsValid(id);
	}
}
