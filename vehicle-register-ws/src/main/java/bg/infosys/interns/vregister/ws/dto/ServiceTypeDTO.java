package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a service type in the application")
public class ServiceTypeDTO {

	@ApiModelProperty(
			value = "Unique identifier of the Survice Type",
			name = "id", dataType = "Integer",
			example = "1", required = true,
			position = 0)
		private Integer id;

	@ApiModelProperty(
			value = "Name of the Survice Type",
			name = "name", dataType = "String",
			example = "tire change",
			required = true,
			position = 1)
	@NotNull(message = "Name cannot be Null")
	@Size(max = 50, message = "Name length must be less than or equal to 50 characters")
	private String name;

	@ApiModelProperty(
			value = "Description of the Survice Type",
			name = "description",
			dataType = "String",
			example = "...",
			required = true,
			position = 2)
	@NotNull(message = "Description cannot be Null")
	@Size(max = 100, message = "Description length must be less than or equal to 100 characters")
	private String description;

	@ApiModelProperty(
			value = "Unique string identifier of the Survice Type",
			name = "code",
			dataType = "String",
			example = "CH213",
			required = true,
			position = 3)
	@NotNull(message = "Code cannot be Null")
	@Size(max = 5, message = "Code length must be less than or equal to 5 characters")
	private String code;
	
	@ApiModelProperty(
			value = "Boolean variable for existing record",
			name = "is_valid",
			dataType = "Boolean",
			example = "true",
			required = true,
			position = 4)
	private Boolean isValid;

	public ServiceTypeDTO() {}

	public ServiceTypeDTO(Integer id, String name, String description, String code, Boolean isValid) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.code = code;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
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
