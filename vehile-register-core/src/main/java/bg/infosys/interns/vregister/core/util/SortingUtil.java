package bg.infosys.interns.vregister.core.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

public class SortingUtil {
private static final String ASC_DIRECTION = "asc";
	
	private SortingUtil() {}
	
	public static <T> void sort(CriteriaQuery<T> cq, CriteriaBuilder cb, Path<Object> path, String sortDirection) {
		if(sortDirection.equalsIgnoreCase(ASC_DIRECTION)) {			
			cq.orderBy(cb.asc(path));
		} else {
			cq.orderBy(cb.desc(path));
		}
	}
	
	public static <T> void sortEx(CriteriaQuery<T> cq, CriteriaBuilder cb, Expression<Double> ex, String sortDirection) {
		if(sortDirection.equalsIgnoreCase(ASC_DIRECTION)) {			
			cq.orderBy(cb.asc(ex));
		} else {
			cq.orderBy(cb.desc(ex));
		}
	}
}
