package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:00:21.743+0300")
@StaticMetamodel(SampProcType.class)
public class SampProcType_ {
	public static volatile SingularAttribute<SampProcType, Long> id_;
	public static volatile SingularAttribute<SampProcType, DataCollType> datacolltype;
	public static volatile ListAttribute<SampProcType, String> content;
	public static volatile SingularAttribute<SampProcType, String> id;
}
