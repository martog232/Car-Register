package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a Brand in the application")
public class BrandDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the brand",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the brand",
			  name = "name",
			  dataType = "String",
			  example = "BMW",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 30, message = "Name length must be less than or equal to 30 characters")
	private String name;
	
	@ApiModelProperty(
			  value = "Two-letter brand code defined in ISO 3166-1",
			  name = "country",
			  dataType = "CountryDTO",
			  required = true,
			  position = 2)
	@NotNull(message = "Country cannot be null")
	private CountryDTO country;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the Brand is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public BrandDTO() {}

	public BrandDTO(Integer id, String name, CountryDTO country, Boolean isValid) {
		this.id = id;
		this.name = name;
		this.country = country;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public CountryDTO getCountry() {
		return country;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
