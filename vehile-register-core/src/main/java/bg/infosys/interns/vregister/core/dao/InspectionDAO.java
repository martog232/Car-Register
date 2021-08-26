package bg.infosys.interns.vregister.core.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import bg.infosys.common.db.dao.GenericDAOImpl;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Address;
import bg.infosys.interns.vregister.core.entity.Brand;
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.Driver;
import bg.infosys.interns.vregister.core.entity.Inspection;
import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.core.entity.ServiceStationService;
import bg.infosys.interns.vregister.core.entity.Vehicle;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class InspectionDAO extends GenericDAOImpl<Inspection, Integer> {
	@Override
	public List<Inspection> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Inspection> criteria = builder.createQuery(Inspection.class);
		Root<Inspection> root = criteria.from(Inspection.class);
		Fetch<Inspection, Vehicle> vehicleFetch = root.fetch(Inspection._vehicle, JoinType.LEFT);
		Fetch<Inspection, ServiceStationService> serviceStationServiceFetch = root.fetch(Inspection._serviceStationServices, JoinType.LEFT);
		
		vehicleFetch.fetch(Vehicle._carType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._fuelType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._driver, JoinType.LEFT)
				    .fetch(Driver._address, JoinType.LEFT)
				    .fetch(Address._city, JoinType.LEFT)
				    .fetch(City._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._brand,JoinType.LEFT)
		    		.fetch(Brand._country, JoinType.LEFT);
		
		serviceStationServiceFetch.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
								  .fetch(ServiceStation._address, JoinType.LEFT)
								  .fetch(Address._city, JoinType.LEFT)
								  .fetch(City._country, JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._serviceType,JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		criteria.select(root).distinct(true);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<Inspection> findAllByFilter(String vehicleRegNumber, String date,String time, Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Inspection> criteria = builder.createQuery(Inspection.class);
		Root<Inspection> root = criteria.from(Inspection.class);

		Fetch<Inspection, Vehicle> vehicleFetch = root.fetch(Inspection._vehicle, JoinType.LEFT);
		Fetch<Inspection, ServiceStationService> serviceStationServiceFetch = root.fetch(Inspection._serviceStationServices, JoinType.LEFT);
		
		vehicleFetch.fetch(Vehicle._carType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._fuelType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._driver, JoinType.LEFT)
				    .fetch(Driver._address, JoinType.LEFT)
				    .fetch(Address._city, JoinType.LEFT)
				    .fetch(City._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._brand,JoinType.LEFT)
		    		.fetch(Brand._country, JoinType.LEFT);
		
		serviceStationServiceFetch.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
								  .fetch(ServiceStation._address, JoinType.LEFT)
								  .fetch(Address._city, JoinType.LEFT)
								  .fetch(City._country, JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._serviceType,JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (vehicleRegNumber != null) {			
			predicates.add(builder.like(builder.lower(root.get(Inspection._vehicle).get(Vehicle._regNumber)), "%" + vehicleRegNumber.toLowerCase() + "%"));
		}
		
		if (date != null) {	
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			predicates.add(builder.equal(root.get(Inspection._date), LocalDate.parse(date, formatter)));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Inspection._isValid), isValid));
		}
		
		SortingUtil.<Inspection>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String vehicleRegNumber, String date, String time, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Inspection> root = criteria.from(Inspection.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (vehicleRegNumber != null) {			
			predicates.add(builder.like(builder.lower(root.get(Inspection._vehicle).get(Vehicle._regNumber)), "%" + vehicleRegNumber.toLowerCase() + "%"));
		}
		
		if (date != null) {			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			
			predicates.add(builder.equal(root.get(Inspection._date), LocalDate.parse(date, formatter)));
		}
		
		if(time!=null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");	
			predicates.add(builder.equal(root.get(Inspection._time), LocalTime.parse(time,formatter)));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Inspection._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public Inspection findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Inspection> criteria = builder.createQuery(Inspection.class);
		Root<Inspection> root = criteria.from(Inspection.class);
		Fetch<Inspection, Vehicle> vehicleFetch = root.fetch(Inspection._vehicle, JoinType.LEFT);
		Fetch<Inspection, ServiceStationService> serviceStationServiceFetch = root.fetch(Inspection._serviceStationServices, JoinType.LEFT);
		
		vehicleFetch.fetch(Vehicle._carType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._fuelType, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._driver, JoinType.LEFT)
				    .fetch(Driver._address, JoinType.LEFT)
				    .fetch(Address._city, JoinType.LEFT)
				    .fetch(City._country, JoinType.LEFT);
		vehicleFetch.fetch(Vehicle._brand,JoinType.LEFT)
		    		.fetch(Brand._country, JoinType.LEFT);
		
		serviceStationServiceFetch.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
								  .fetch(ServiceStation._address, JoinType.LEFT)
								  .fetch(Address._city, JoinType.LEFT)
								  .fetch(City._country, JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._serviceType,JoinType.LEFT);
		serviceStationServiceFetch.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		predicates.add(builder.equal(root.get("id"), id));
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		try {
			return createQuery(criteria).getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}
}
