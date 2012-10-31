package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:54:11.970+0300")
@StaticMetamodel(NationType.class)
public class NationType_ {
	public static volatile SingularAttribute<NationType, Long> id_;
	public static volatile SingularAttribute<NationType, SumDscrType> sumdscrtype;
	public static volatile ListAttribute<NationType, String> content;
	public static volatile SingularAttribute<NationType, String> id;
	public static volatile SingularAttribute<NationType, String> abbr;
}
