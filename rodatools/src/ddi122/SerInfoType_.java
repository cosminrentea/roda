package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.616+0300")
@StaticMetamodel(SerInfoType.class)
public class SerInfoType_ {
	public static volatile SingularAttribute<SerInfoType, Long> id_;
	public static volatile SingularAttribute<SerInfoType, SerStmtType> serstmttype;
	public static volatile ListAttribute<SerInfoType, String> content;
	public static volatile SingularAttribute<SerInfoType, String> id;
	public static volatile SingularAttribute<SerInfoType, String> xmlLang;
	public static volatile SingularAttribute<SerInfoType, String> source;
}
