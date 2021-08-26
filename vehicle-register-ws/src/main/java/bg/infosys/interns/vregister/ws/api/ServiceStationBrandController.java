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
import bg.infosys.interns.vregister.ws.dto.ServiceStationBrandDTO;
import bg.infosys.interns.vregister.ws.service.ServiceStationBrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "ServiceStationBrand")
@RequestMapping(ControllerConfig.SERVICE_STATIONS_BRANDS_URL)
public class ServiceStationBrandController {
	private final ServiceStationBrandService serviceStationBrandService;

	@Autowired
	public ServiceStationBrandController(ServiceStationBrandService serviceStationBrandService) {
		this.serviceStationBrandService = serviceStationBrandService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all ServiceStationBrands", notes = "This method will return all ServiceStationBrands")
	public List<ServiceStationBrandDTO> findAll() {
		return serviceStationBrandService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of ServiceStationBrands", notes = "This method will return page of ServiceStationBrands")
	public Page<ServiceStationBrandDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "isValid", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String serviceStationName,
			@RequestParam(required = false) String brandName,
			@RequestParam(required = false) Boolean isValid) {
		return serviceStationBrandService.findAllByFilter(serviceStationName, brandName, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one ServiceStationBrand", notes = "This method will return the ServiceStationBrand matching the passed id or HTTP Status 404 Not Found")
	public ServiceStationBrandDTO findById(@PathVariable Integer id) {
		return serviceStationBrandService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new ServiceStationBrand", notes = "This method will create new ServiceStationBrand or HTTP Status 400 Bad Request")
	public ServiceStationBrandDTO create(@RequestBody @Valid ServiceStationBrandDTO serviceStationBrandDTO) {
		return serviceStationBrandService.save(serviceStationBrandDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update ServiceStationBrand", notes = "This method will update the ServiceStationBrand with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public ServiceStationBrandDTO update(@PathVariable Integer id, @RequestBody @Valid ServiceStationBrandDTO serviceStationBrandDTO) {
		return serviceStationBrandService.update(serviceStationBrandDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of ServiceStationBrand", notes = "This method will change the isValid of the ServiceStationBrand from true to false and vice versa.")
	public ServiceStationBrandDTO changeIsValid(@PathVariable Integer id) {
		return serviceStationBrandService.changeIsValid(id);
	}
}
