package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:52.203+0300")
@StaticMetamodel(AbstractType.class)
public class AbstractType_ {
	public static volatile SingularAttribute<AbstractType, Long> id_;
	public static volatile SingularAttribute<AbstractType, StdyInfoType> stdyinfotype;
	public static volatile ListAttribute<AbstractType, String> content;
	public static volatile SingularAttribute<AbstractType, String> id;
	public static volatile SingularAttribute<AbstractType, String> xmlLang;
	public static volatile SingularAttribute<AbstractType, String> source;
	public static volatile SingularAttribute<AbstractType, String> date;
}
