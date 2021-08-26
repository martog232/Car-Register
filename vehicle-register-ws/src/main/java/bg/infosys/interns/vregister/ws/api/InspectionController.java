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
import bg.infosys.interns.vregister.ws.dto.InspectionDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.InspectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Inspections")
@RequestMapping(ControllerConfig.INSPECTIONS_URL)
public class InspectionController {
	private final InspectionService inspectionService;

	@Autowired
	public InspectionController(InspectionService inspectionService) {
		this.inspectionService = inspectionService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Inspections", notes = "This method will return all Inspections")
	public List<InspectionDTO> findAll() {
		return inspectionService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Inspections", notes = "This method will return page of Inspections")
	public Page<InspectionDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String vehicleRegNumber,  
			@RequestParam(required = false) String date, 
			@RequestParam(required = false) String time,
			@RequestParam(required = false) Boolean isValid) {
		return inspectionService.findAllByFilter(vehicleRegNumber, date, time, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Inspection", notes = "This method will return the Inspection matching the passed id or HTTP Status 404 Not Found")
	public InspectionDTO findById(@PathVariable Integer id) {
		return inspectionService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Inspection", notes = "This method will create new Inspection or HTTP Status 400 Bad Request")
	public InspectionDTO create(@RequestBody @Valid InspectionDTO inspectionDTO) {
		return inspectionService.save(inspectionDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Inspection", notes = "This method will update the Inspection with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public InspectionDTO update(@PathVariable Integer id, @RequestBody @Valid InspectionDTO inspectionDTO) {
		return inspectionService.update(inspectionDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Inspection", notes = "This method will change the isValid of the Inspection from true to false and vice versa.")
	public InspectionDTO changeIsValid(@PathVariable Integer id) {
		return inspectionService.changeIsValid(id);
	}
}
	
