package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.097+0300")
@StaticMetamodel(DataAccsType.class)
public class DataAccsType_ {
	public static volatile SingularAttribute<DataAccsType, Long> id_;
	public static volatile SingularAttribute<DataAccsType, StdyDscrType> stdydscrtype;
	public static volatile ListAttribute<DataAccsType, SetAvailType> setAvail;
	public static volatile ListAttribute<DataAccsType, UseStmtType> useStmt;
	public static volatile SingularAttribute<DataAccsType, String> id;
	public static volatile SingularAttribute<DataAccsType, String> xmlLang;
	public static volatile SingularAttribute<DataAccsType, String> source;
}
