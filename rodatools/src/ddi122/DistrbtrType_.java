package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:45:30.071+0300")
@StaticMetamodel(DistrbtrType.class)
public class DistrbtrType_ {
	public static volatile SingularAttribute<DistrbtrType, Long> id_;
	public static volatile SingularAttribute<DistrbtrType, DistStmtType> diststmttype;
	public static volatile ListAttribute<DistrbtrType, String> content;
	public static volatile SingularAttribute<DistrbtrType, String> id;
	public static volatile SingularAttribute<DistrbtrType, String> abbr;
	public static volatile SingularAttribute<DistrbtrType, String> affiliation;
	public static volatile SingularAttribute<DistrbtrType, String> uri;
}
