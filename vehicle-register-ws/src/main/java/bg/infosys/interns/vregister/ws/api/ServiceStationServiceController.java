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
import bg.infosys.interns.vregister.ws.dto.ServiceStationServiceDTO;
import bg.infosys.interns.vregister.ws.service.ServiceStationServiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "ServiceStationService")
@RequestMapping(ControllerConfig.SERVICE_STATIONS_SERVICES_URL)
public class ServiceStationServiceController {
	private final ServiceStationServiceService serviceStationServiceService;

	@Autowired
	public ServiceStationServiceController(ServiceStationServiceService serviceStationServiceService) {
		this.serviceStationServiceService = serviceStationServiceService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all ServiceStationServices", notes = "This method will return all ServiceStationServices")
	public List<ServiceStationServiceDTO> findAll() {
		return serviceStationServiceService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of ServiceStationServices", notes = "This method will return page of ServiceStationServices")
	public Page<ServiceStationServiceDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "isValid", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String serviceStationName,
			@RequestParam(required = false) String serviceTypeName,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String cityName,
			@RequestParam(required = false) String countryName,
			@RequestParam(required = false) Integer price,
			@RequestParam(required = false) Double hourDuration,
			@RequestParam(required = false) String fuelTypeName,
			@RequestParam(required = false) Boolean isValid) {
		return serviceStationServiceService.findAllByFilter(serviceStationName, serviceTypeName,address, cityName,countryName,price,hourDuration,fuelTypeName, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one ServiceStationService", notes = "This method will return the ServiceStationService matching the passed id or HTTP Status 404 Not Found")
	public ServiceStationServiceDTO findById(@PathVariable Integer id) {
		return serviceStationServiceService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new ServiceStationService", notes = "This method will create new ServiceStationService or HTTP Status 400 Bad Request")
	public ServiceStationServiceDTO create(@RequestBody @Valid ServiceStationServiceDTO serviceStationServiceDTO) {
		return serviceStationServiceService.save(serviceStationServiceDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update ServiceStationService", notes = "This method will update the ServiceStationService with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public ServiceStationServiceDTO update(@PathVariable Integer id, @RequestBody @Valid ServiceStationServiceDTO serviceStationServiceDTO) {
		return serviceStationServiceService.update(serviceStationServiceDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of ServiceStationService", notes = "This method will change the isValid of the ServiceStationService from true to false and vice versa.")
	public ServiceStationServiceDTO changeIsValid(@PathVariable Integer id) {
		return serviceStationServiceService.changeIsValid(id);
	}
}
