package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:56:46.014+0300")
@StaticMetamodel(OthIdType.class)
public class OthIdType_ {
	public static volatile SingularAttribute<OthIdType, Long> id_;
	public static volatile SingularAttribute<OthIdType, RspStmtType> rspstmttype;
	public static volatile ListAttribute<OthIdType, PType> p;
	public static volatile ListAttribute<OthIdType, OthIdType> othId;
	public static volatile SingularAttribute<OthIdType, OthIdType> othidtype;
	public static volatile SingularAttribute<OthIdType, String> id;
	public static volatile SingularAttribute<OthIdType, String> type;
	public static volatile SingularAttribute<OthIdType, String> role;
	public static volatile SingularAttribute<OthIdType, String> affiliation;
}
