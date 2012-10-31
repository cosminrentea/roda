package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:00:32.209+0300")
@StaticMetamodel(SerInfoType.class)
public class SerInfoType_ {
	public static volatile SingularAttribute<SerInfoType, Long> id_;
	public static volatile SingularAttribute<SerInfoType, SerStmtType> serstmttype;
	public static volatile ListAttribute<SerInfoType, String> content;
	public static volatile SingularAttribute<SerInfoType, String> id;
}
