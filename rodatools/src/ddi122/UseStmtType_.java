package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.743+0300")
@StaticMetamodel(UseStmtType.class)
public class UseStmtType_ {
	public static volatile SingularAttribute<UseStmtType, Long> id_;
	public static volatile SingularAttribute<UseStmtType, DataAccsType> dataaccess;
	public static volatile SingularAttribute<UseStmtType, SpecPermType> specPerm;
	public static volatile ListAttribute<UseStmtType, ContactType> contact;
	public static volatile SingularAttribute<UseStmtType, CitReqType> citReq;
	public static volatile SingularAttribute<UseStmtType, DeposReqType> deposReq;
	public static volatile SingularAttribute<UseStmtType, DisclaimerType> disclaimer;
	public static volatile SingularAttribute<UseStmtType, String> id;
	public static volatile SingularAttribute<UseStmtType, String> xmlLang;
	public static volatile SingularAttribute<UseStmtType, String> source;
}
