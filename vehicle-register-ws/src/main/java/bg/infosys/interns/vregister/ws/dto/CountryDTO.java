package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a country in the application")
public class CountryDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the Country",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the Country",
			  name = "name",
			  dataType = "String",
			  example = "Bulgaria",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 30, message = "Name length must be less than or equal to 30 charecters")
	private String name;
	
	@ApiModelProperty(
			  value = "Two-letter country code defined in ISO 3166-1",
			  name = "code",
			  dataType = "String",
			  example = "BG",
			  required = true,
			  position = 2)
	@NotNull(message = "Code cannot be null")
	@Size(max = 2, message = "Code length must be less or equal to 2 characters")
	private String code;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the country is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public CountryDTO() {}

	public CountryDTO(Integer id, String name, String code, Boolean isValid) {
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
