package bg.infosys.interns.vregister.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "public",name = "vehicles")
public class Vehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "year")
	private Integer year;
	public static final String _year = "year";
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_type_id")
    private CarType carType;
	public static final String _carType="carType"; 
	
	@Column(name = "seats")
	private Integer seats;
	public static final String _seats = "seats";

	@Column(name = "doors")
	private Integer doors;
	public static final String _doors = "doors";
	
	@Column(name = "color")
	private String color;
	public static final String _color = "color";	

	@Column(name = "power")
	private Integer power;
	public static final String _power = "power";
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fuel_type_id")
    private FuelType fuelType;
	public static final String _fuelType = "fuelType"; 
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country_id")
    private Country country;
	public static final String _country = "country"; 
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="driver_id")
    private Driver driver;
	public static final String _driver = "driver" ;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id")
    private Brand brand;
	public static final String _brand = "brand"; 
	
	@Column(name = "reg_number")
	private String regNumber;
	public static final String _regNumber = "regNumber";
	
	@Column(name = "is_valid")
	private Boolean isValid;
	public static final String _isValid = "isValid";

	public Vehicle() {}

    public Vehicle(Integer id, Integer year, CarType carType, Integer seats, Integer doors, String color, Integer power,
			FuelType fuelType, Country country, Driver driver, Brand brand, String regNumber, Boolean isValid) {
		super();
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

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public void setIsValid(Boolean isValid) {
		this.isValid=isValid;
	}

	public Boolean getIsValid() {
		return isValid;
	}
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
