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
import bg.infosys.interns.vregister.core.entity.Driver;
import bg.infosys.interns.vregister.core.entity.ServiceType;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class ServiceTypeDAO extends GenericDAOImpl<ServiceType, Integer> {
	public List<ServiceType> findAllByFilter(String name,String description, String code,Boolean isValid,  PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceType> criteria = builder.createQuery(ServiceType.class);
		Root<ServiceType> root = criteria.from(ServiceType.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (description != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._description)), "%" + description.toLowerCase() + "%"));
		}
		
		if (code != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._code)), "%" + code.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Driver._isValid), isValid));
		}
		
		SortingUtil.<ServiceType>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public List<ServiceType> findAllByCode(String code) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceType> criteria = builder.createQuery(ServiceType.class);
		Root<ServiceType> root = criteria.from(ServiceType.class);
			
		List<Predicate> predicates = new ArrayList<Predicate>();
			
		if (code != null) {			
			predicates.add(builder.equal(root.get(ServiceType._code), code));
		}
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
			
		return createQuery(criteria).getResultList();
	}


	public long countAllByFilter(String name,String description, String code,Boolean isValid) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ServiceType> root = criteria.from(ServiceType.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (description != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._description)), "%" + description.toLowerCase() + "%"));
		}
		
		if (code != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceType._code)), "%" + code.toLowerCase() + "%"));
		}
		
		if (isValid != null) {			
			predicates.add(builder.equal(root.get(Driver._isValid), isValid));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
}
