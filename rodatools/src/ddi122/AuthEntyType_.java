package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:54.934+0300")
@StaticMetamodel(AuthEntyType.class)
public class AuthEntyType_ {
	public static volatile SingularAttribute<AuthEntyType, Long> id_;
	public static volatile SingularAttribute<AuthEntyType, RspStmtType> rspstmttype;
	public static volatile ListAttribute<AuthEntyType, String> content;
	public static volatile SingularAttribute<AuthEntyType, String> id;
	public static volatile SingularAttribute<AuthEntyType, String> xmlLang;
	public static volatile SingularAttribute<AuthEntyType, String> source;
	public static volatile SingularAttribute<AuthEntyType, String> affiliation;
}
