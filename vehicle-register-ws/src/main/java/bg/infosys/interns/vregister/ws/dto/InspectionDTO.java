package bg.infosys.interns.vregister.ws.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class InspectionDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the Inspection",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Vehicle on which the inspection was made",
			  name = "vehicle",
			  dataType = "VehicleDTO",
			  required = true,
			  position = 1)
	private VehicleDTO vehicle;
	
	@ApiModelProperty(
			  value = "Set of ServiceStationServices that were made during the inspection",
			  name = "serviceStationServices",
			  dataType = "Set<ServiceStationService>",
			  required = true,
			  position = 2)
	private Set<ServiceStationServiceDTO> serviceStationServices;
	
	@ApiModelProperty(
			  value = "Date of the Inspection",
			  name = "date",
			  dataType = "String",
			  required = true,
			  position = 3)
	@NotNull(message = "Date cannot be null")
	private String date;
	
	@ApiModelProperty(
			  value = "Time of the Inspection",
			  name = "time",
			  dataType = "String",
			  required = true,
			  position = 3)
	@NotNull(message = "Time cannot be null")
	private String time;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the inspection is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 4)
	private Boolean isValid;

	public InspectionDTO() {}

	public InspectionDTO(Integer id, VehicleDTO vehicle, Set<ServiceStationServiceDTO> serviceStationServices, String date,String time, Boolean isValid) {
		this.id = id;
		this.vehicle = vehicle;
		this.serviceStationServices = serviceStationServices;
		this.date = date;
		this.time=time;
		this.isValid = isValid;
	}
	
	public Integer getId() {
		return id;
	}

	public VehicleDTO getVehicle() {
		return vehicle;
	}

	public Set<ServiceStationServiceDTO> getServiceStationServices() {
		return serviceStationServices;
	}

	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
