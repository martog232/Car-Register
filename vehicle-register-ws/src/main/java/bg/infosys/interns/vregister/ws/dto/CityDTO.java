package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class CityDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the City",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the City",
			  name = "name",
			  dataType = "String",
			  example = "Sofia",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 30, message = "Name length must be less than or equal to 30 characters")
	private String name;
	
	@ApiModelProperty(
			  value = "Country where the city is located",
			  name = "country",
			  dataType = "CountryDTO",
			  required = true,
			  position = 2)
	private CountryDTO country;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the city is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public CityDTO() {}

	public CityDTO(Integer id, String name, CountryDTO country, Boolean isValid) {
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
