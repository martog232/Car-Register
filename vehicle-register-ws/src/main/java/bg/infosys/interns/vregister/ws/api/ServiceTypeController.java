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

import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.dto.ServiceTypeDTO;
import bg.infosys.interns.vregister.ws.service.ServiceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.ws.config.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags="ServiceTypes")
@RequestMapping(ControllerConfig.SERVICE_TYPES_URL)
public class ServiceTypeController {
	private final ServiceTypeService serviceTypeService;
	
	@Autowired 
	public ServiceTypeController(ServiceTypeService serviceTypeService) {
		this.serviceTypeService = serviceTypeService;
	}
	
	@GetMapping 
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all ServiceTypes", notes = "This method will return all ServiceTypes")
	public List<ServiceTypeDTO> findAll() {
		return serviceTypeService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of ServiceType", notes = "This method will return page of ServiceTypes")
	public Page<ServiceTypeDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) Boolean isValid)
	{
		return serviceTypeService.findAllByFilter(name, description, code,isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one ServiceType", notes = "Returns the ServiceType matching the passed id or HTTP Status 404 Not Found")
	public ServiceTypeDTO findById(@PathVariable Integer id) {
		return  serviceTypeService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new ServiceType", notes = "Creates new ServiceType")
	public ServiceTypeDTO create(@RequestBody @Valid ServiceTypeDTO serviceType) {
		return  serviceTypeService.save(serviceType);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update ServiceType", notes = "Updates the ServiceType with given id or will return HTTP Status 404 Not Found")
	public ServiceTypeDTO update(@PathVariable Integer id, @RequestBody @Valid ServiceTypeDTO serviceType) {
		return serviceTypeService.update(serviceType, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID +"/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "change isValid of ServiceType", notes = "Changes isValid in ServiceType matching the passed id")
	public ServiceTypeDTO changeIsValid(@PathVariable Integer id) {
		return serviceTypeService.changeIsValid(id);
	}
}
