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
@Table(schema = "public", name = "r_service_stations_services")
public class ServiceStationService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_station_id")
	private ServiceStation serviceStation;
	public static final String _serviceStation = "serviceStation";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id")
	private ServiceType serviceType;
	public static final String _serviceType = "serviceType";

	@Column(name = "price")
	private Integer price;
	public static final String _price = "price";

	@Column(name = "hour_duration")
	private Double hourDuration;
	public static final String _hourDuration = "hourDuration";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fuel_type_id")
	private FuelType fuelType;
	public static final String _fuelType = "fuelType";
	
	@Column(name = "is_valid")
	private Boolean isValid;
	public static final String _isValid = "isValid";

	public ServiceStationService() {}

	public ServiceStationService(Integer id, ServiceStation serviceStation, ServiceType serviceType, Integer price,
			Double hourDuration, FuelType fuelType,Boolean isValid) {
		this.id = id;
		this.serviceStation = serviceStation;
		this.serviceType = serviceType;
		this.price = price;
		this.hourDuration = hourDuration;
		this.fuelType = fuelType;
		this.isValid=isValid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ServiceStation getServiceStation() {
		return serviceStation;
	}

	public void setServiceStation(ServiceStation serviceStation) {
		this.serviceStation = serviceStation;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Double getHourDuration() {
		return hourDuration;
	}

	public void setHourDuration(Double hourDuration) {
		this.hourDuration = hourDuration;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
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
		ServiceStationService other = (ServiceStationService) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
