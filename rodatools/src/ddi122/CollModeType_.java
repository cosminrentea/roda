package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.077+0300")
@StaticMetamodel(CollModeType.class)
public class CollModeType_ {
	public static volatile SingularAttribute<CollModeType, Long> id_;
	public static volatile SingularAttribute<CollModeType, DataCollType> datacolltype;
	public static volatile ListAttribute<CollModeType, String> content;
	public static volatile SingularAttribute<CollModeType, String> id;
	public static volatile SingularAttribute<CollModeType, String> xmlLang;
	public static volatile SingularAttribute<CollModeType, String> source;
}
