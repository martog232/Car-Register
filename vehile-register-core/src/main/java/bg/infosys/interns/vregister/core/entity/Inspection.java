package bg.infosys.interns.vregister.core.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "public",name = "inspections")
public class Inspection{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;
	public static final String _vehicle = "vehicle";
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "r_inspections_services", joinColumns = @JoinColumn(name = "inspection_id"), inverseJoinColumns = @JoinColumn(name = "service_station_service_id"))
	private Set<ServiceStationService> serviceStationServices;
	public static final String _serviceStationServices = "serviceStationServices";

	@Column(name = "date")
	private LocalDate date;
	public static final String _date = "date";
	
	@Column(name="time")
	private LocalTime time;
	public static final String _time="time";
	
	@Column(name = "is_valid")
	private Boolean isValid;
	public static final String _isValid = "isValid";

	public Inspection() {}

	public Inspection(Integer id, Vehicle vehicle, Set<ServiceStationService> serviceStationServices, LocalDate date, LocalTime time, Boolean isValid) {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDate getDate() {
    	return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getTime() {
    	return time;
    }
    
    public void setTime(LocalTime time) {
    	this.time=time;
    }
    
    public Set<ServiceStationService> getServiceStationServices() {
		return serviceStationServices;
	}

	public void setServiceStationServices(Set<ServiceStationService> serviceStationServices) {
		this.serviceStationServices = serviceStationServices;
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
		Inspection other = (Inspection) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
