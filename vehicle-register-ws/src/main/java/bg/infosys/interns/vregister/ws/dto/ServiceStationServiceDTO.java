package bg.infosys.interns.vregister.ws.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Class representing a Services that Service station offer in the application")
public class ServiceStationServiceDTO {
	 @ApiModelProperty(
	    	 value = "Unique identifier of the ServiceStationService",
	   			  name = "id",
	   			  dataType = "Integer",
	   			  example = "1",
	   			  required = true,
	   			  position = 0)
		private Integer id;
	 
	 @ApiModelProperty(
			  value = "ServiceStation",
			  name = "serviceStation",
			  dataType = "ServiceStationDTO",
			  required = true,
			  position = 1)
	private ServiceStationDTO serviceStation;

	 @ApiModelProperty(
			  value = "offer ServiceType",
			  name = "serviceType",
			  dataType = "ServiceTypeDTO",
			  required = true,
			  position = 2)
	private ServiceTypeDTO serviceType;
	 
	 @ApiModelProperty(
		  value = "price of ServiceType",
		  name = "price",
		  dataType = "Integer",
		  required = true,
		  position = 3)
	 private Integer price;
	 
	 @ApiModelProperty(
			  value = "hour duration of ServiceType",
			  name = "hourDuration",
			  dataType = "Double",
			  required = true,
			  position = 4)
		 private Double hourDuration;
	 
	 @ApiModelProperty(
			  value = "FuelType",
			  name = "fuelType",
			  dataType = "FuelTypeDTO",
			  required = true,
			  position = 5)
		 private FuelTypeDTO fuelType;
	 
	 @ApiModelProperty(
			  value = "Variable indicating whether the ServiceType in ServiceStation is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 7)
	private Boolean isValid;
	 
	  public ServiceStationServiceDTO() {}
	    
	    public ServiceStationServiceDTO(Integer id,ServiceStationDTO serviceStation, ServiceTypeDTO serviceType,
	    		Integer price,Double hourDuration, FuelTypeDTO fuelType, Boolean isValid){
	    	
	        this.id=id;
	        this.serviceStation=serviceStation;
	        this.serviceType=serviceType;
	        this.price=price;
	        this.hourDuration=hourDuration;
	        this.fuelType=fuelType;
	        this.isValid=isValid;
	    }

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public ServiceStationDTO getServiceStation() {
	        return serviceStation;
	    }

	    public ServiceTypeDTO getServiceType() {
	        return serviceType;
	    }

	    public Integer getPrice() {
	        return price;
	    }
	    
	    public Double getHourDuration() {
	        return hourDuration;
	    }
	    
	    public FuelTypeDTO getFuelType() {
	        return fuelType;
	    }
	    
	    public Boolean getIsValid(){
	        return isValid;
	    }
	    
	    public void setIsValid(Boolean isValid) {
			this.isValid = isValid;
		}
	}
