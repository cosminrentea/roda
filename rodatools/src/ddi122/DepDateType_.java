package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:43:28.918+0300")
@StaticMetamodel(DepDateType.class)
public class DepDateType_ {
	public static volatile SingularAttribute<DepDateType, Long> id_;
	public static volatile SingularAttribute<DepDateType, DistStmtType> diststmttype;
	public static volatile ListAttribute<DepDateType, String> content;
	public static volatile SingularAttribute<DepDateType, String> id;
	public static volatile SingularAttribute<DepDateType, String> date;
}
