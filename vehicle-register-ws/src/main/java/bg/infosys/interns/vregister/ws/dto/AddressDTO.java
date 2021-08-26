package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class AddressDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the Address",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "City where the address is located",
			  name = "city",
			  dataType = "CityDTO",
			  required = true,
			  position = 1)
	private CityDTO city;
	
	@ApiModelProperty(
			  value = "Address details (street, street number etc).",
			  name = "address",
			  dataType = "String",
			  example = "23 Graf Ignatiev Street",
			  required = true,
			  position = 2)
	@NotNull(message = "Address cannot be null")
	@Size(max = 60, message = "Address length must be less than or equal to 60 characters")
	private String address;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the address is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public AddressDTO() {}

	public AddressDTO(Integer id, CityDTO city, String address, Boolean isValid) {
		this.id = id;
		this.city = city;
		this.address = address;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public CityDTO getCity() {
		return city;
	}

	public String getAddress() {
		return address;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
