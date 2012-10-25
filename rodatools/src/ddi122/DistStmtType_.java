package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:42.149+0300")
@StaticMetamodel(DistStmtType.class)
public class DistStmtType_ {
	public static volatile SingularAttribute<DistStmtType, Long> id_;
	public static volatile ListAttribute<DistStmtType, DistrbtrType> distrbtr;
	public static volatile ListAttribute<DistStmtType, ContactType> contact;
	public static volatile ListAttribute<DistStmtType, DepositrType> depositr;
	public static volatile ListAttribute<DistStmtType, DepDateType> depDate;
	public static volatile SingularAttribute<DistStmtType, String> id;
	public static volatile SingularAttribute<DistStmtType, String> xmlLang;
	public static volatile SingularAttribute<DistStmtType, String> source;
}
