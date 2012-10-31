package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:31:21.673+0300")
@StaticMetamodel(AuthEntyType.class)
public class AuthEntyType_ {
	public static volatile SingularAttribute<AuthEntyType, Long> id_;
	public static volatile SingularAttribute<AuthEntyType, RspStmtType> rspstmttype;
	public static volatile ListAttribute<AuthEntyType, String> content;
	public static volatile SingularAttribute<AuthEntyType, String> id;
	public static volatile SingularAttribute<AuthEntyType, String> affiliation;
}
