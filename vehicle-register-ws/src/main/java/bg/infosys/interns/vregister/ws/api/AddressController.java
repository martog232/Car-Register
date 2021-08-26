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
import bg.infosys.interns.vregister.ws.dto.AddressDTO;
import bg.infosys.interns.vregister.ws.dto.Page;
import bg.infosys.interns.vregister.ws.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Api(tags = "Addresses")
@RequestMapping(ControllerConfig.ADDRESSES_URL)
public class AddressController {
	private final AddressService addressService;

	@Autowired
	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find all Addresses", notes = "This method will return all Addresses")
	public List<AddressDTO> findAll() {
		return addressService.findAll();
	}
	
	@GetMapping("/paging")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get one page of Addresses", notes = "This method will return page of Addresses")
	public Page<AddressDTO> findAllByFilter(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id", required = false) String sortBy,
			@RequestParam(defaultValue = "asc", required = false) String sortDirection,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String cityName,
			@RequestParam(required = false) Boolean isValid) {
		return addressService.findAllByFilter(address, cityName, isValid, new PagingSorting(page, size, sortBy, sortDirection));
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Find one Address", notes = "This method will return the Address matching the passed id or HTTP Status 404 Not Found")
	public AddressDTO findById(@PathVariable Integer id) {
		return addressService.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create new Address", notes = "This method will create new Address or HTTP Status 400 Bad Request")
	public AddressDTO create(@RequestBody @Valid AddressDTO addressDTO) {
		return addressService.save(addressDTO);		
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update Address", notes = "This method will update the Address with given id or will return HTTP Status 404 Not Found or HTTP Status 400 Bad Request")
	public AddressDTO update(@PathVariable Integer id, @RequestBody @Valid AddressDTO addressDTO) {
		return addressService.update(addressDTO, id);
	}
	
	@PutMapping(ControllerConfig.CHANGE_IS_VALID + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Change isValid of Address", notes = "This method will change the isValid of the address from true to false and vice versa.")
	public AddressDTO changeIsValid(@PathVariable Integer id) {
		return addressService.changeIsValid(id);
	}
}
