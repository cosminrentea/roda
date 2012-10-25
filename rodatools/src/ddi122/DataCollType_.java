package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.135+0300")
@StaticMetamodel(DataCollType.class)
public class DataCollType_ {
	public static volatile SingularAttribute<DataCollType, Long> id_;
	public static volatile SingularAttribute<DataCollType, MethodType> methodtype;
	public static volatile ListAttribute<DataCollType, TimeMethType> timeMeth;
	public static volatile ListAttribute<DataCollType, DataCollectorType> dataCollector;
	public static volatile ListAttribute<DataCollType, SampProcType> sampProc;
	public static volatile ListAttribute<DataCollType, CollModeType> collMode;
	public static volatile ListAttribute<DataCollType, ResInstruType> resInstru;
	public static volatile SingularAttribute<DataCollType, SourcesType> sources;
	public static volatile ListAttribute<DataCollType, WeightType> weight;
	public static volatile SingularAttribute<DataCollType, String> id;
	public static volatile SingularAttribute<DataCollType, String> xmlLang;
	public static volatile SingularAttribute<DataCollType, String> source;
}
