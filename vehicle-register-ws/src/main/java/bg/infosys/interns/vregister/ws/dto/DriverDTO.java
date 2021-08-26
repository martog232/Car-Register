package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a driver in the application")
public class DriverDTO {
	 @ApiModelProperty(
    	 value = "Unique identifier of the Driver",
   			  name = "id",
   			  dataType = "Integer",
   			  example = "1",
   			  required = true,
   			  position = 0)
	private Integer id;
	 
	 @ApiModelProperty(
			  value = "Name of the driver",
			  name = "name",
			  dataType = "String",
			  example = "Martin Georgiev",
			  required = true,
			  position = 1)
	@NotNull(message = "Name cannot be null")
	@Size(max = 50, message = "Name length must be less than or equal to 50 charecters")
	private String name;
	 
	 @ApiModelProperty(
			  value = "Address where the driver is live",
			  name = "address",
			  dataType = "AddressDTO",
			  required = true,
			  position = 2)
	private AddressDTO address;
	 
	 @ApiModelProperty(
			  value = "Driver's age",
			  name = "age",
			  dataType = "Integer",
			  required = true,
			  position = 3)
	private Short age; 
	 
	 @ApiModelProperty(
			  value = "Variable indicating whether the driver is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 4)
	private Boolean isValid;
	
    public DriverDTO() {}
    
    public DriverDTO(Integer id,String name, AddressDTO address, Short age, Boolean isValid){
    	
        this.id=id;
        this.name=name;
        this.address=address;
        this.age=age;
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
    
    public void setName(String name) {
		this.name = name;
	}

    public AddressDTO getAddress() {
        return address;
    }
    
    public void setAddress(AddressDTO address) {
		this.address = address;
	}

    public Short getAge() {
        return age;
    }
    
    public void setAge(Short age) {
		this.age = age;
	}
    
    public Boolean getIsValid(){
        return isValid;
    }
    public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
