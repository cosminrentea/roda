package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.701+0300")
@StaticMetamodel(TimeMethType.class)
public class TimeMethType_ {
	public static volatile SingularAttribute<TimeMethType, Long> id_;
	public static volatile SingularAttribute<TimeMethType, DataCollType> datacolltype;
	public static volatile ListAttribute<TimeMethType, String> content;
	public static volatile SingularAttribute<TimeMethType, String> id;
	public static volatile SingularAttribute<TimeMethType, String> xmlLang;
	public static volatile SingularAttribute<TimeMethType, String> source;
	public static volatile SingularAttribute<TimeMethType, String> method;
}
