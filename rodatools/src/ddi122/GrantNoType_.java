package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:50:20.127+0300")
@StaticMetamodel(GrantNoType.class)
public class GrantNoType_ {
	public static volatile SingularAttribute<GrantNoType, Long> id_;
	public static volatile SingularAttribute<GrantNoType, ProdStmtType> prodstmttype;
	public static volatile ListAttribute<GrantNoType, String> content;
	public static volatile SingularAttribute<GrantNoType, String> id;
	public static volatile SingularAttribute<GrantNoType, String> agency;
	public static volatile SingularAttribute<GrantNoType, String> role;
}
