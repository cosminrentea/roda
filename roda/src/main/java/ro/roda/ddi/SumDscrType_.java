package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:02:41.009+0300")
@StaticMetamodel(SumDscrType.class)
public class SumDscrType_ {
	public static volatile SingularAttribute<SumDscrType, Long> id_;
	public static volatile SingularAttribute<SumDscrType, StdyInfoType> stdyinfotype;
	public static volatile ListAttribute<SumDscrType, TimePrdType> timePrd;
	public static volatile ListAttribute<SumDscrType, CollDateType> collDate;
	public static volatile ListAttribute<SumDscrType, NationType> nation;
	public static volatile ListAttribute<SumDscrType, GeogCoverType> geogCover;
	public static volatile ListAttribute<SumDscrType, GeogUnitType> geogUnit;
	public static volatile ListAttribute<SumDscrType, AnlyUnitType> anlyUnit;
	public static volatile ListAttribute<SumDscrType, UniverseType> universe;
	public static volatile SingularAttribute<SumDscrType, String> id;
}
