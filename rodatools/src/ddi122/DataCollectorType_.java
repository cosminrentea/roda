package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.129+0300")
@StaticMetamodel(DataCollectorType.class)
public class DataCollectorType_ {
	public static volatile SingularAttribute<DataCollectorType, Long> id_;
	public static volatile SingularAttribute<DataCollectorType, DataCollType> datacolltype;
	public static volatile ListAttribute<DataCollectorType, String> content;
	public static volatile SingularAttribute<DataCollectorType, String> id;
	public static volatile SingularAttribute<DataCollectorType, String> xmlLang;
	public static volatile SingularAttribute<DataCollectorType, String> source;
	public static volatile SingularAttribute<DataCollectorType, String> abbr;
	public static volatile SingularAttribute<DataCollectorType, String> affiliation;
}
