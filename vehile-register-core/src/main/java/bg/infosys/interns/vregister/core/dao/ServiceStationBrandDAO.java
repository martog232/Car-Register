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
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.core.entity.ServiceStationBrand;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class ServiceStationBrandDAO extends GenericDAOImpl<ServiceStationBrand, Integer> {
	
	@Override
	public List<ServiceStationBrand> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationBrand> criteria = builder.createQuery(ServiceStationBrand.class);
		Root<ServiceStationBrand> root = criteria.from(ServiceStationBrand.class);

		root.fetch(ServiceStationBrand._serviceStation, JoinType.LEFT)
		    .fetch(ServiceStation._address, JoinType.LEFT)
		    .fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		root.fetch(ServiceStationBrand._brand, JoinType.LEFT)
		    .fetch(Brand._country, JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<ServiceStationBrand> findAllByFilter(String serviceStationName, String brandName, Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationBrand> criteria = builder.createQuery(ServiceStationBrand.class);
		Root<ServiceStationBrand> root = criteria.from(ServiceStationBrand.class);

		root.fetch(ServiceStationBrand._serviceStation, JoinType.LEFT)
	    	.fetch(ServiceStation._address, JoinType.LEFT)
	    	.fetch(Address._city, JoinType.LEFT)
	    	.fetch(City._country, JoinType.LEFT);
	
		root.fetch(ServiceStationBrand._brand, JoinType.LEFT)
	    	.fetch(Brand._country, JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (serviceStationName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationBrand._serviceStation).get(ServiceStation._name)), "%" + serviceStationName.toLowerCase() + "%"));
		}
		
		if (brandName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationBrand._brand).get(Brand._name)), "%" + brandName.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStationBrand._isValid), isValid));
		}
		
		SortingUtil.<ServiceStationBrand>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String serviceStationName, String brandName, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ServiceStationBrand> root = criteria.from(ServiceStationBrand.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		

		if (serviceStationName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationBrand._serviceStation).get(ServiceStation._name)), "%" + serviceStationName.toLowerCase() + "%"));
		}
		
		if (brandName != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStationBrand._brand).get(Brand._name)), "%" + brandName.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStationBrand._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public ServiceStationBrand findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStationBrand> criteria = builder.createQuery(ServiceStationBrand.class);
		Root<ServiceStationBrand> root = criteria.from(ServiceStationBrand.class);

		root.fetch(ServiceStationBrand._serviceStation, JoinType.LEFT)
	    	.fetch(ServiceStation._address, JoinType.LEFT)
	    	.fetch(Address._city, JoinType.LEFT)
	    	.fetch(City._country, JoinType.LEFT);
	
		root.fetch(ServiceStationBrand._brand, JoinType.LEFT)
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
