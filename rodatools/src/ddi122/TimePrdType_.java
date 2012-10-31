package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:03:17.184+0300")
@StaticMetamodel(TimePrdType.class)
public class TimePrdType_ {
	public static volatile SingularAttribute<TimePrdType, Long> id_;
	public static volatile SingularAttribute<TimePrdType, SumDscrType> sumdscrtype;
	public static volatile ListAttribute<TimePrdType, String> content;
	public static volatile SingularAttribute<TimePrdType, String> id;
	public static volatile SingularAttribute<TimePrdType, String> date;
	public static volatile SingularAttribute<TimePrdType, String> event;
	public static volatile SingularAttribute<TimePrdType, String> cycle;
}
