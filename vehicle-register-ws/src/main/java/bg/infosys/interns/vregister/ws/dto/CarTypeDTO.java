package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a CarType in the application")
public class CarTypeDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the CarType",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the CarType",
			  name = "name",
			  dataType = "String",
			  example = "SUV",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 10, message = "Name length must be less than or equal to 10 charecters")
	private String name;
	
	@ApiModelProperty(
			  value = "CarType code",
			  name = "code",
			  dataType = "String",
			  example = "1",
			  required = true,
			  position = 2)
	@NotNull(message = "Code cannot be null")
	@Size(max = 1, message = "Code length must be less or equal to 1 characters")
	private String code;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the CarType is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public CarTypeDTO() {}

	public CarTypeDTO(Integer id, String name, String code, Boolean isValid) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
