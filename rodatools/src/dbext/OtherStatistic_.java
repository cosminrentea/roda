package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.117+0300")
@StaticMetamodel(OtherStatistic.class)
public class OtherStatistic_ {
	public static volatile SingularAttribute<OtherStatistic, Integer> id;
	public static volatile SingularAttribute<OtherStatistic, Integer> instanceId;
	public static volatile SingularAttribute<OtherStatistic, String> statisticName;
	public static volatile SingularAttribute<OtherStatistic, Double> statisticValue;
	public static volatile SingularAttribute<OtherStatistic, EditedVariable> editedVariable;
}
