package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:37:55.863+0300")
@StaticMetamodel(CollDateType.class)
public class CollDateType_ {
	public static volatile SingularAttribute<CollDateType, Long> id_;
	public static volatile SingularAttribute<CollDateType, SumDscrType> sumdscrtype;
	public static volatile ListAttribute<CollDateType, String> content;
	public static volatile SingularAttribute<CollDateType, String> id;
	public static volatile SingularAttribute<CollDateType, String> date;
	public static volatile SingularAttribute<CollDateType, String> event;
	public static volatile SingularAttribute<CollDateType, String> cycle;
}
