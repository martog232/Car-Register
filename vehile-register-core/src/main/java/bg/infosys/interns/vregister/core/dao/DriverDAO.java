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
import bg.infosys.interns.vregister.core.entity.Driver;
import bg.infosys.interns.vregister.core.entity.Address;
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class DriverDAO extends GenericDAOImpl<Driver, Integer> {

	@Override
	public List<Driver> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Driver> criteria = builder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		root.fetch(Driver._address,JoinType.LEFT)
			.fetch(Address._city,JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		criteria.select(root);
		return createQuery(criteria).getResultList();
	}
	
	public List<Driver> findAllByFilter(String name, String address, String city,String country, Short age,Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Driver> criteria = builder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		root.fetch(Driver._address,JoinType.LEFT)
			.fetch(Address._city,JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (city != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._city).get(City._name)), "%" + city.toLowerCase() + "%"));
		}
		
		if (country != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._city).get(City._country).get(Country._name)), "%" + country.toLowerCase() + "%"));
		}
		
		if (age!= null) {
			predicates.add(builder.equal(root.get(Driver._age),age));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Driver._isValid), isValid));
		}
		
		SortingUtil.<Driver>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String name, String address, String city, String country, Short age,Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Driver> root = criteria.from(Driver.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (city!=null) {
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._city).get(City._name)), "%" + city.toLowerCase() + "%"));
		}
		
		if (country!=null) {
			predicates.add(builder.like(builder.lower(root.get(Driver._address).get(Address._city).get(City._country).get(Country._name)), "%" + country.toLowerCase() + "%"));
		}
		if (age!= null) {
			predicates.add(builder.equal(root.get(Driver._age),age));
		}
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Driver._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public Driver findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Driver> criteria = builder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		root.fetch(Driver._address)
	        .fetch(Address._city, JoinType.LEFT)
	        .fetch(City._country,JoinType.LEFT);
		
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
