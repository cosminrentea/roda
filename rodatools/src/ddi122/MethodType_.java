package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.388+0300")
@StaticMetamodel(MethodType.class)
public class MethodType_ {
	public static volatile SingularAttribute<MethodType, Long> id_;
	public static volatile SingularAttribute<MethodType, StdyDscrType> stdydscrtype;
	public static volatile ListAttribute<MethodType, DataCollType> dataColl;
	public static volatile SingularAttribute<MethodType, String> id;
	public static volatile SingularAttribute<MethodType, String> xmlLang;
	public static volatile SingularAttribute<MethodType, String> source;
}
