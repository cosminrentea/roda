package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.843+0300")
@StaticMetamodel(WeightType.class)
public class WeightType_ {
	public static volatile SingularAttribute<WeightType, Long> id_;
	public static volatile SingularAttribute<WeightType, DataCollType> datacolltype;
	public static volatile ListAttribute<WeightType, String> content;
	public static volatile SingularAttribute<WeightType, String> id;
	public static volatile SingularAttribute<WeightType, String> xmlLang;
	public static volatile SingularAttribute<WeightType, String> source;
}
