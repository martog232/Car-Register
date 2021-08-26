package bg.infosys.interns.vregister.ws.dto;

import io.swagger.annotations.ApiModelProperty;

public class ServiceStationBrandDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the ServiceStationBrand",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "ServiceStation that serves the Brand",
			  name = "serviceStation",
			  dataType = "ServiceStationDTO",
			  required = true,
			  position = 1)
	private ServiceStationDTO serviceStation;
	
	@ApiModelProperty(
			  value = "Brand that is serviced by the ServiceStation",
			  name = "brand",
			  dataType = "BrandDTO",
			  required = true,
			  position = 2)
	private BrandDTO brand;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the ServiceStationBrand is valid.",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 3)
	private Boolean isValid;

	public ServiceStationBrandDTO() {}

	public ServiceStationBrandDTO(Integer id, ServiceStationDTO serviceStation, BrandDTO brand, Boolean isValid) {
		this.id = id;
		this.serviceStation = serviceStation;
		this.brand = brand;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public ServiceStationDTO getServiceStation() {
		return serviceStation;
	}

	public BrandDTO getBrand() {
		return brand;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
