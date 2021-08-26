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
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class AddressDAO extends GenericDAOImpl<Address, Integer> {

	@Override
	public List<Address> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Address> criteria = builder.createQuery(Address.class);
		Root<Address> root = criteria.from(Address.class);

		root.fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<Address> findAllByFilter(String address, String cityName, Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Address> criteria = builder.createQuery(Address.class);
		Root<Address> root = criteria.from(Address.class);

		root.fetch(Address._city, JoinType.LEFT)
	        .fetch(City._country, JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (cityName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Address._city).get(City._name)), "%" + cityName.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Address._isValid), isValid));
		}
		
		SortingUtil.<Address>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String address, String cityName, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Address> root = criteria.from(Address.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (address != null) {			
			predicates.add(builder.like(builder.lower(root.get(Address._address)), "%" + address.toLowerCase() + "%"));
		}
		
		if (cityName != null) {			
			predicates.add(builder.like(builder.lower(root.get(Address._city).get(City._name)), "%" + cityName.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Address._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public Address findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Address> criteria = builder.createQuery(Address.class);
		Root<Address> root = criteria.from(Address.class);

		root.fetch(Address._city, JoinType.LEFT)
	        .fetch(City._country, JoinType.LEFT);
		
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
