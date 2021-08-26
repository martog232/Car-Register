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
@Table(schema = "public", name = "service_stations")
public class ServiceStation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
 
	@Column(name = "name")
	private String name;
	public static final String _name = "name";
 
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
	private Address address;
	public static final String _address = "address";

	@Column(name = "is_valid")
	private Boolean isValid;
	public static final String _isValid = "isValid";
	
	@Column(name="start_work_time")
	private Integer startWorkTime;
	public static final String _startWorkTime="startWorkTime";
	
	@Column(name="off_work_time")
	private Integer offWorkTime;
	public static final String _offWorkTime="offWorkTime";

	public ServiceStation() {}

	public ServiceStation(Integer id, String name, Address address, Boolean isValid,Integer startWorkTime,Integer offWorkTime) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.isValid = isValid;
		this.startWorkTime=startWorkTime;
		this.offWorkTime=offWorkTime;
		
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
 
	public Address getAddress() {
		return address;
	}
 
	public void setAddress(Address address) {
		this.address = address;
	}
 
	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	public Integer getStartWorkTime() {
		return startWorkTime;
	}
	
	public void setStartWorkTime(Integer startWorkTime) {
		this.startWorkTime=startWorkTime;
	}
	
	public Integer getOffWorkTime() {
		return offWorkTime;
	}
	
	public void setOffWorkTime(Integer offWorkTime) {
		this.offWorkTime=offWorkTime;
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
		ServiceStation other = (ServiceStation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}