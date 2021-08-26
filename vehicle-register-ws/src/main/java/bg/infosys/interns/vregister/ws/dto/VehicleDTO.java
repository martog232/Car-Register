package bg.infosys.interns.vregister.ws.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

public class VehicleDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the Vehicle",
			  name = "id",
			  dataType = "Integer",
			  example = "1",
			  required = true,
			  position = 0)
	private Integer id;
	
	@ApiModelProperty(
			  value = "Year in which the car was manufactured",
			  name = "year",
			  dataType = "Integer",
			  example = "2010",
			  required = true,
			  position = 1)
	@NotNull(message = "Year cannot be null")
//	@Positive(message = "Year should be positive number")
	private Integer year;
	
	@ApiModelProperty(
			  value = "Type of the vehicle",
			  name = "carType",
			  dataType = "CarTypeDTO",
			  required = true,
			  position = 2)
	private CarTypeDTO carType;
	
	@ApiModelProperty(
			  value = "Number of seats that the vehicle has",
			  name = "seats",
			  dataType = "Integer",
			  example = "4",
			  required = true,
			  position = 3)
	@NotNull(message = "Seats cannot be null")
//	@Positive(message = "Seats should be positive number")
	private Integer seats;
	
	@ApiModelProperty(
			  value = "Number of doors that the vehicle has",
			  name = "doors",
			  dataType = "Integer",
			  example = "4",
			  required = true,
			  position = 4)
	@NotNull(message = "Doors cannot be null")
//	@Positive(message = "Doors should be positive number")
	private Integer doors;
	

	@ApiModelProperty(
			  value = "Color of the vehicle",
			  name = "color",
			  dataType = "String",
			  example = "blue",
			  required = true,
			  position = 5)
	@NotNull(message = "Color cannot be null")
	@Size(max = 20, message = "Color length must be less than or equal to 20 characters")
	private String color;
	
	@ApiModelProperty(
			  value = "Horse power that the vehicle has",
			  name = "power",
			  dataType = "Integer",
			  example = "160",
			  required = true,
			  position = 6)
	@NotNull(message = "Power cannot be null")
//	@Positive(message = "Power should be positive number")
	private Integer power;
	
	@ApiModelProperty(
			  value = "Fuel type of the vehicle",
			  name = "fuelType",
			  dataType = "FuelTypeDTO",
			  required = true,
			  position = 7)
	private FuelTypeDTO fuelType;
	
	@ApiModelProperty(
			  value = "Country where the vehicle is manufacture",
			  name = "country",
			  dataType = "CountryDTO",
			  required = true,
			  position = 8)
	private CountryDTO country;
	
	@ApiModelProperty(
			  value = "Driver of the vehicle",
			  name = "driver",
			  dataType = "DriverDTO",
			  required = true,
			  position = 9)
	private DriverDTO driver;
	
	@ApiModelProperty(
			  value = "Brand of the vehicle",
			  name = "brand",
			  dataType = "BrandDTO",
			  required = true,
			  position = 10)
	private BrandDTO brand;
	
	@ApiModelProperty(
			  value = "Registration number of the vehicle",
			  name = "regNumbe",
			  dataType = "String",
			  example = "CA 1234 PB",
			  required = true,
			  position = 11)
	@NotNull(message = "Registration number cannot be null")
	@Size(max = 10, message = "Registration number length must be less than or equal to 10 characters")
	private String regNumber;
	
	@ApiModelProperty(
			  value = "Variable indicating whether the vehicle is valid",
			  name = "isValid",
			  dataType = "Boolean",
			  example = "true",
			  required = true,
			  position = 12)
	private Boolean isValid;
	
	public VehicleDTO() {}

	public VehicleDTO(Integer id, Integer year, CarTypeDTO carType, Integer seats, Integer doors, String color,
			          Integer power, FuelTypeDTO fuelType, CountryDTO country, DriverDTO driver, BrandDTO brand, 
			          String regNumber, Boolean isValid) {
		this.id = id;
		this.year = year;
		this.carType = carType;
		this.seats = seats;
		this.doors = doors;
		this.color = color;
		this.power = power;
		this.fuelType = fuelType;
		this.country = country;
		this.driver = driver;
		this.brand = brand;
		this.regNumber = regNumber;
		this.isValid = isValid;
	}

	public Integer getId() {
		return id;
	}

	public Integer getYear() {
		return year;
	}

	public CarTypeDTO getCarType() {
		return carType;
	}

	public Integer getSeats() {
		return seats;
	}

	public Integer getDoors() {
		return doors;
	}

	public String getColor() {
		return color;
	}

	public Integer getPower() {
		return power;
	}

	public FuelTypeDTO getFuelType() {
		return fuelType;
	}

	public CountryDTO getCountry() {
		return country;
	}

	public DriverDTO getDriver() {
		return driver;
	}

	public BrandDTO getBrand() {
		return brand;
	}

	public String getRegNumber() {
		return regNumber;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
}
