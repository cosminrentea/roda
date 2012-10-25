package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:42.858+0300")
@StaticMetamodel(RspStmtType.class)
public class RspStmtType_ {
	public static volatile SingularAttribute<RspStmtType, Long> id_;
	public static volatile ListAttribute<RspStmtType, AuthEntyType> authEnty;
	public static volatile ListAttribute<RspStmtType, OthIdType> othId;
	public static volatile SingularAttribute<RspStmtType, String> id;
	public static volatile SingularAttribute<RspStmtType, String> xmlLang;
	public static volatile SingularAttribute<RspStmtType, String> source;
}
