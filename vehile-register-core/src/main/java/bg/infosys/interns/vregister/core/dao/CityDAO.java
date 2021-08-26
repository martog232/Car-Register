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
import bg.infosys.interns.vregister.core.entity.City;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class CityDAO extends GenericDAOImpl<City, Integer> {
	
	@Override
	public List<City> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<City> criteria = builder.createQuery(City.class);
		Root<City> root = criteria.from(City.class);
		
		root.fetch(City._country, JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<City> findAllByFilter(String name, String countryName, Boolean isValid, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<City> criteria = builder.createQuery(City.class);
		Root<City> root = criteria.from(City.class);
		
		root.fetch(City._country, JoinType.LEFT);

		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(City._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(City._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(City._isValid), isValid));
		}
		
		SortingUtil.<City>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}

	public long countAllByFilter(String name, String countryName, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<City> root = criteria.from(City.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(City._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (countryName != null) {			
			predicates.add(builder.like(builder.lower(root.get(City._country).get(Country._name)), "%" + countryName.toLowerCase() + "%"));
		}
		

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(City._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public City findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<City> criteria = builder.createQuery(City.class);
		Root<City> root = criteria.from(City.class);
		
		root.fetch(City._country, JoinType.LEFT);
		
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
