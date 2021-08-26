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
import bg.infosys.interns.vregister.core.entity.Brand;
import bg.infosys.interns.vregister.core.entity.CarType;
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.core.entity.Driver;
import bg.infosys.interns.vregister.core.entity.FuelType;
import bg.infosys.interns.vregister.core.entity.Vehicle;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class VehicleDAO extends GenericDAOImpl<Vehicle, Integer> {
	
	@Override
	public List<Vehicle> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Vehicle> criteria = builder.createQuery(Vehicle.class);
		Root<Vehicle> root = criteria.from(Vehicle.class);

		root.fetch(Vehicle._carType, JoinType.LEFT);
		
		root.fetch(Vehicle._fuelType, JoinType.LEFT);
		
		root.fetch(Vehicle._country, JoinType.LEFT);
		
		root.fetch(Vehicle._driver, JoinType.LEFT)
		    .fetch(Driver._address, JoinType.LEFT)
		    .fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(Vehicle._brand,JoinType.LEFT)
		    .fetch(Brand._country, JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<Vehicle> findAllByFilter(Integer year, 
			                             String carTypeName, 
			                             Integer seats, 
			                             Integer doors, 
			                             String color, 
			                             Integer power, 
			                             String fuelTypeName, 
			                             String countryName, 
			                             String driverName, 
			                             String brandName, 
			                             String regNumber, 
			                             Boolean isValid, 
			                             PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Vehicle> criteria = builder.createQuery(Vehicle.class);
		Root<Vehicle> root = criteria.from(Vehicle.class);

        root.fetch(Vehicle._carType, JoinType.LEFT);
		
		root.fetch(Vehicle._fuelType, JoinType.LEFT);
		
		root.fetch(Vehicle._country, JoinType.LEFT);
		
		root.fetch(Vehicle._driver, JoinType.LEFT)
		    .fetch(Driver._address, JoinType.LEFT)
		    .fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(Vehicle._brand,JoinType.LEFT)
		    .fetch(Brand._country, JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (year != null) {			
			predicates.add(builder.equal(root.get(Vehicle._year), year));
		}
		
		if (carTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._carType).get(CarType._name)), "%" + carTypeName.toLowerCase() + "%"));
		}
		
		if (seats != null) {			
			predicates.add(builder.equal(root.get(Vehicle._seats), seats));
		}
		
		if (doors != null) {			
			predicates.add(builder.equal(root.get(Vehicle._doors), doors));
		}
		
		if (color != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._color)), "%" + color.toLowerCase() + "%"));
		}
		
		if (power != null) {			
			predicates.add(builder.equal(root.get(Vehicle._power), power));
		}
		
		if (fuelTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._fuelType).get(FuelType._name)), "%" + fuelTypeName.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		
		if (driverName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._driver).get(Driver._name)), "%" + driverName.toLowerCase() + "%"));
		}
		
		if (brandName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._brand).get(Brand._name)), "%" + brandName.toLowerCase() + "%"));
		}
		
		if (regNumber != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._regNumber)), "%" + regNumber.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Vehicle._isValid), isValid));
		}
		
		SortingUtil.<Vehicle>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(Integer year, 
                                 String carTypeName, 
                                 Integer seats, 
                                 Integer doors, 
                                 String color, 
                                 Integer power, 
                                 String fuelTypeName, 
                                 String countryName, 
                                 String driverName, 
                                 String brandName, 
                                 String regNumber, 
                                 Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Vehicle> root = criteria.from(Vehicle.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (year != null) {			
			predicates.add(builder.equal(root.get(Vehicle._year), year));
		}
		
		if (carTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._carType).get(CarType._name)), "%" + carTypeName.toLowerCase() + "%"));
		}
		
		if (seats != null) {			
			predicates.add(builder.equal(root.get(Vehicle._seats), seats));
		}
		
		if (doors != null) {			
			predicates.add(builder.equal(root.get(Vehicle._doors), doors));
		}
		
		if (color != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._color)), "%" + color.toLowerCase() + "%"));
		}
		
		if (power != null) {			
			predicates.add(builder.equal(root.get(Vehicle._power), power));
		}
		
		if (fuelTypeName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._fuelType).get(FuelType._name)), "%" + fuelTypeName.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		
		if (driverName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._driver).get(Driver._name)), "%" + driverName.toLowerCase() + "%"));
		}
		
		if (brandName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._brand).get(Brand._name)), "%" + brandName.toLowerCase() + "%"));
		}
		
		if (regNumber != null) {			
			predicates.add(builder.like(builder.lower(root.get(Vehicle._regNumber)), "%" + regNumber.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Vehicle._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public Vehicle findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Vehicle> criteria = builder.createQuery(Vehicle.class);
		Root<Vehicle> root = criteria.from(Vehicle.class);

		root.fetch(Vehicle._carType, JoinType.LEFT);
		
		root.fetch(Vehicle._fuelType, JoinType.LEFT);
		
		root.fetch(Vehicle._country, JoinType.LEFT);
		
		root.fetch(Vehicle._driver, JoinType.LEFT)
		    .fetch(Driver._address, JoinType.LEFT)
		    .fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(Vehicle._brand,JoinType.LEFT)
		    .fetch(Brand._country, JoinType.LEFT);
		
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
