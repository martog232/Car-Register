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
import bg.infosys.interns.vregister.core.entity.ServiceStation;
import bg.infosys.interns.vregister.core.util.SortingUtil;

@Repository
public class ServiceStationDAO extends GenericDAOImpl<ServiceStation, Integer> {

	@Override
	public List<ServiceStation> findAll() {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStation> criteria = builder.createQuery(ServiceStation.class);
		Root<ServiceStation> root = criteria.from(ServiceStation.class);

		root.fetch(ServiceStation._address, JoinType.LEFT)
		    .fetch(Address._city, JoinType.LEFT)
		    .fetch(City._country, JoinType.LEFT);
		
		criteria.select(root);
		
		return createQuery(criteria).getResultList();
	}
	
	public List<ServiceStation> findAllByFilter(String name, String addressAddress, Boolean isValid,Integer startWorkTime,Integer offWorkTime, PagingSorting pagingSorting) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStation> criteria = builder.createQuery(ServiceStation.class);
		Root<ServiceStation> root = criteria.from(ServiceStation.class);

		root.fetch(ServiceStation._address, JoinType.LEFT)
	        .fetch(Address._city, JoinType.LEFT)
            .fetch(City._country, JoinType.LEFT);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStation._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (addressAddress != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStation._address).get(Address._address)), "%" + addressAddress.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStation._isValid), isValid));
		}
		
		if (startWorkTime!=null) {
			predicates.add(builder.equal(root.get(ServiceStation._startWorkTime), startWorkTime));
		}
		
		if (offWorkTime!=null) {
			predicates.add(builder.equal(root.get(ServiceStation._offWorkTime), offWorkTime));
		}
		
		SortingUtil.<ServiceStation>sort(criteria, builder, root.get(pagingSorting.getSortBy()), pagingSorting.getSortDirection());
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).setFirstResult(pagingSorting.getPageSize() * pagingSorting.getPageNumber())
									.setMaxResults(pagingSorting.getPageSize())
									.getResultList();
	}
	
	public long countAllByFilter(String name, String addressAddress, Boolean isValid,Integer startWorkTime,Integer offWorkTime) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<ServiceStation> root = criteria.from(ServiceStation.class);
		
		criteria.select(builder.countDistinct(root));
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (name != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStation._name)), "%" + name.toLowerCase() + "%"));
		}
		
		if (addressAddress != null) {			
			predicates.add(builder.like(builder.lower(root.get(ServiceStation._address).get(Address._address)), "%" + addressAddress.toLowerCase() + "%"));
		}

		if (isValid != null) {			
			predicates.add(builder.equal(root.get(ServiceStation._isValid), isValid));
		}
		
		if (startWorkTime!=null) {
			predicates.add(builder.equal(root.get(ServiceStation._startWorkTime), startWorkTime));
		}
		
		if (offWorkTime!=null) {
			predicates.add(builder.equal(root.get(ServiceStation._offWorkTime), offWorkTime));
		}
		
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		
		return createQuery(criteria).getSingleResult();
	}
	
	@Override
	public ServiceStation findById(Integer id) {
		CriteriaBuilder builder = criteriaBuilder();
		CriteriaQuery<ServiceStation> criteria = builder.createQuery(ServiceStation.class);
		Root<ServiceStation> root = criteria.from(ServiceStation.class);

		root.fetch(ServiceStation._address, JoinType.LEFT)
            .fetch(Address._city, JoinType.LEFT)
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
