package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:42.981+0300")
@StaticMetamodel(SerStmtType.class)
public class SerStmtType_ {
	public static volatile SingularAttribute<SerStmtType, Long> id_;
	public static volatile ListAttribute<SerStmtType, SerNameType> serName;
	public static volatile ListAttribute<SerStmtType, SerInfoType> serInfo;
	public static volatile SingularAttribute<SerStmtType, String> id;
	public static volatile SingularAttribute<SerStmtType, String> xmlLang;
	public static volatile SingularAttribute<SerStmtType, String> source;
	public static volatile SingularAttribute<SerStmtType, String> uri;
}
