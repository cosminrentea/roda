package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-04T14:36:08.972+0200")
@StaticMetamodel(DistrbtrType.class)
public class DistrbtrType_ {
	public static volatile SingularAttribute<DistrbtrType, Long> id_;
	public static volatile SingularAttribute<DistrbtrType, DistStmtType> diststmttype;
	public static volatile SingularAttribute<DistrbtrType, String> content;
	public static volatile SingularAttribute<DistrbtrType, String> id;
	public static volatile SingularAttribute<DistrbtrType, String> abbr;
	public static volatile SingularAttribute<DistrbtrType, String> affiliation;
	public static volatile SingularAttribute<DistrbtrType, String> uri;
}
