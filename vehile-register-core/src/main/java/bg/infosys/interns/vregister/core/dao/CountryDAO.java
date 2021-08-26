package bg.infosys.interns.vregister.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import bg.infosys.common.db.dao.GenericDAOImpl;
import bg.infosys.interns.vregister.core.dto.PagingSorting;
import bg.infosys.interns.vregister.core.entity.Country;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class CountryDAO extends GenericDAOImpl<Country, Integer> {
	public List<Country> findAllByFilter(String name, String code, Boolean isValid,  PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
		Root<Country> root = criteria.from(Country.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(Country._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (code != null) {			
			predicates.add(builder.like(builder.lower(root.get(Country._code)), "%" + code.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Country._isValid), isValid));
		}
		
		SortingUtil.<Country>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	
	public List<Country> findAllByCode(String code) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
		Root<Country> root = criteria.from(Country.class);
			
		List<Predicate> predicates = new ArrayList<Predicate>();
			
		if (code != null) {			
			predicates.add(builder.equal(root.get(Country._code), code));
		}
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			
		return createQuery(criteria).getResultList();
	}

	public long countAllByFilter(String name, String code, Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Country> root = criteria.from(Country.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(Country._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (code != null) {			
			predicates.add(builder.like(builder.lower(root.get(Country._code)), "%" + code.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Country._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
}
