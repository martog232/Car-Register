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
import bg.infosys.interns.vregister.ws.dto.BrandDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Brands")
@RequestMapping(ControllerConfig.BRANDS_URL)
public class BrandController {
	private final BrandService brandService;

	@Autowired
	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Brands", notes = "This method will return all Brands")
	public List<BrandDTO> findAll() {
		return brandService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Brands", notes = "This method will return page of Brands")
	public Page<BrandDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "name", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String countryName,
			@RequestParam(required = false) Boolean isValid) {
		return brandService.findAllByFilter(name, countryName, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Brand", notes = "This method will return the Brand matching the passed id or HTTP Status 404 Not Found")
	public BrandDTO findById(@PathVariable Integer id) {
		return brandService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Brand", notes = "This method will create new Brand or HTTP Status 400 Bad Request")
	public BrandDTO create(@RequestBody @Valid BrandDTO brandDTO) {
		return brandService.save(brandDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Brand", notes = "This method will update the Brand with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public BrandDTO update(@PathVariable Integer id, @RequestBody @Valid BrandDTO brandDTO) {
		return brandService.update(brandDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Brand", notes = "This method change isValid of the Brand from true to false and vice versa.")
	public BrandDTO changeIsValid(@PathVariable Integer id) {
		return brandService.changeIsValid(id);
	}
}
