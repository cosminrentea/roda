package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T17:36:59.739+0200")
@StaticMetamodel(StdyDscrType.class)
public class StdyDscrType_ {
	public static volatile SingularAttribute<StdyDscrType, Long> id_;
	public static volatile SingularAttribute<StdyDscrType, CodeBook> codebook;
	public static volatile ListAttribute<StdyDscrType, CitationType> citation;
	public static volatile ListAttribute<StdyDscrType, StdyInfoType> stdyInfo;
	public static volatile ListAttribute<StdyDscrType, MethodType> method;
	public static volatile ListAttribute<StdyDscrType, DataAccsType> dataAccs;
	public static volatile ListAttribute<StdyDscrType, OthrStdyMatType> othrStdyMat;
	public static volatile SingularAttribute<StdyDscrType, String> id;
}
