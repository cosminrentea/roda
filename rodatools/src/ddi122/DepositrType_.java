package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:24:53.877+0300")
@StaticMetamodel(DepositrType.class)
public class DepositrType_ {
	public static volatile SingularAttribute<DepositrType, Long> id_;
	public static volatile SingularAttribute<DepositrType, DistStmtType> diststmttype;
	public static volatile ListAttribute<DepositrType, String> content;
	public static volatile SingularAttribute<DepositrType, String> id;
	public static volatile SingularAttribute<DepositrType, String> abbr;
}
