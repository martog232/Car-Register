package bg.infosys.interns.vregister.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import bg.infosys.common.db.dao.GenericDAOImpl;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Address;
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.core.entity.FuelType;
import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.core.entity.ServiceStationService;
import bg.infosys.interns.vregister.core.entity.ServiceType;
import bg.infosys.interns.vregister.core.entity.Vehicle;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class ServiceStationServiceDAO extends GenericDAOImpl<ServiceStationService, Integer> {
	
	@Override
	public List<ServiceStationService> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationService> criteria = builder.createQuery(ServiceStationService.class);
		Root<ServiceStationService> root = criteria.from(ServiceStationService.class);
		
		root.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
	    .fetch(ServiceStation._address, JoinType.LEFT)
	    .fetch(Address._city, JoinType.LEFT)
	    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(ServiceStationService._serviceType,JoinType.LEFT);
		
		root.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<ServiceStationService> findAllByFilter(String serviceStationName,String serviceTypeName,String address,String cityName,String countryName, Integer price,Double hourDuration,String fuelTypeName, Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationService> criteria = builder.createQuery(ServiceStationService.class);
		Root<ServiceStationService> root = criteria.from(ServiceStationService.class);

		root.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
	    .fetch(ServiceStation._address, JoinType.LEFT)
	    .fetch(Address._city, JoinType.LEFT)
	    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(ServiceStationService._serviceType, JoinType.LEFT);
		
		root.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (serviceStationName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._name)), "%" + serviceStationName.toLowerCase() + "%"));
		}
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (cityName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._city).get(City._name)), "%" + cityName.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._city).get(City._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		
		if (serviceTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceType).get(ServiceType._name)), "%" + serviceTypeName.toLowerCase() + "%"));
		}
		
		if (fuelTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._fuelType).get(FuelType._name)), "%" + fuelTypeName.toLowerCase() + "%"));
		}
		
		if (price!=null) {
			predicates.add(builder.equal(root.get(ServiceStationService._price),price));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStationService._isValid), isValid));
		}
		
		SortingUtil.<ServiceStationService>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String serviceStationName, String serviceTypeName,String address,String cityName, String countryName,  Integer price, Double hourDuration,String fuelTypeName, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ServiceStationService> root = criteria.from(ServiceStationService.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		

		if (serviceStationName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._name)), "%" + serviceStationName.toLowerCase() + "%"));
		}
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (cityName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._city).get(City._name)), "%" + cityName.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceStation).get(ServiceStation._address).get(Address._city).get(City._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		
		if (serviceTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._serviceType).get(ServiceType._name)), "%" + serviceTypeName.toLowerCase() + "%"));
		}
		
		if (fuelTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationService._fuelType).get(FuelType._name)), "%" + fuelTypeName.toLowerCase() + "%"));
		}
		
		if (price!=null) {
			predicates.add(builder.equal(root.get(ServiceStationService._price),price));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStationService._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public ServiceStationService findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationService> criteria = builder.createQuery(ServiceStationService.class);
		Root<ServiceStationService> root = criteria.from(ServiceStationService.class);
		
		root.fetch(ServiceStationService._serviceStation, JoinType.LEFT)
		.fetch(ServiceStation._address, JoinType.LEFT)
    	.fetch(Address._city, JoinType.LEFT)
    	.fetch(City._country, JoinType.LEFT);
		
		root.fetch(ServiceStationService._serviceType,JoinType.LEFT);
		
		root.fetch(ServiceStationService._fuelType,JoinType.LEFT);
		
		
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
