package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.682+0300")
@StaticMetamodel(StdyInfoType.class)
public class StdyInfoType_ {
	public static volatile SingularAttribute<StdyInfoType, Long> id_;
	public static volatile SingularAttribute<StdyInfoType, StdyDscrType> stdydscrtype;
	public static volatile SingularAttribute<StdyInfoType, SubjectType> subject;
	public static volatile ListAttribute<StdyInfoType, AbstractType> _abstract;
	public static volatile ListAttribute<StdyInfoType, SumDscrType> sumDscr;
	public static volatile SingularAttribute<StdyInfoType, String> id;
	public static volatile SingularAttribute<StdyInfoType, String> xmlLang;
	public static volatile SingularAttribute<StdyInfoType, String> source;
}
