package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:00:42.363+0300")
@StaticMetamodel(SerNameType.class)
public class SerNameType_ {
	public static volatile SingularAttribute<SerNameType, Long> id_;
	public static volatile SingularAttribute<SerNameType, SerStmtType> serstmttype;
	public static volatile ListAttribute<SerNameType, String> content;
	public static volatile SingularAttribute<SerNameType, String> id;
	public static volatile SingularAttribute<SerNameType, String> abbr;
}
