package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a fuelType in the application")
public class FuelTypeDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the Fuel type",
			  name = "id",
			  dataType = "Integer",
			  example = "1806",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Name of the fuel type",
			  name = "name",
			  dataType = "String",
			  example = "diesel",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 10, message = "Name length must be less than or equal to 10 characters")
	private String name;
	
	@ApiModelProperty(
			value = "Unique string identifier of the fuel Type",
			name = "code",
			dataType = "String",
			example = "C213",
			required = true,
			position = 2)
	@NotNull(message = "Code cannot be null")
	@Size(max = 10, message = "Code length must be less than or equal to 10 characters")
	private String code; 
	
	@ApiModelProperty(
			value = "Boolean variable for existing record",
			name = "is_valid",
			dataType = "Boolean",
			example = "true",
			required = true,
			position = 4)
	private Boolean isValid;
	
  public FuelTypeDTO() {}

  public FuelTypeDTO(Integer id,String name,String code, Boolean isValid){
      this.id=id;
      this.name=name;
      this.code=code;
      this.isValid=isValid;
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

  public String getCode() {
      return code;
  }
  
  public Boolean getIsValid(){
      return isValid;
  }
  public void setIsValid(Boolean isValid) {
	  this.isValid=isValid;
  }
}
