package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class ServiceStationDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the service station",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the service station",
			  name = "name",
			  dataType = "String",
			  example = "Radial",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 30, message = "Name length must be less than or equal to 30 characters")
	private String name;
	
	@ApiModelProperty(
			  value = "Address where the service station is located",
			  name = "address",
			  dataType = "AddressDTO",
			  required = true,
			  position = 2)
	private AddressDTO address;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the service station is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;
	
	@ApiModelProperty(
			  value = "Variable indicating when Service Station opens",
			  name = "startWorkTime",
			  dataType = "Integer",
			  example = "8",
			  required = true,
			  position = 4)
	private Integer startWorkTime;
	
	@ApiModelProperty(
			  value = "Variable indicating when Service Station closes",
			  name = "offWorkTime",
			  dataType = "Integer",
			  example = "16",
			  required = true,
			  position = 5)
	private Integer offWorkTime;

	public ServiceStationDTO() {}

	public ServiceStationDTO(Integer id, String name, AddressDTO address, Boolean isValid , Integer startWorkTime,Integer offWorkTime) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.isValid = isValid;
		this.startWorkTime=startWorkTime;
		this.offWorkTime=offWorkTime;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public AddressDTO getAddress() {
		return address;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	public Integer getStartWorkTime() {
		return startWorkTime;
	}
	
	public Integer getOffWorkTime() {
		return offWorkTime;
	}
}
