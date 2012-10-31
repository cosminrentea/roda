package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:36:54.118+0300")
@StaticMetamodel(SumStatType.class)
public class SumStatType_ {
	public static volatile SingularAttribute<SumStatType, Long> id_;
	public static volatile SingularAttribute<SumStatType, VarType> vartype;
	public static volatile ListAttribute<SumStatType, String> content;
	public static volatile SingularAttribute<SumStatType, String> id;
	public static volatile ListAttribute<SumStatType, String> wgtVar;
	public static volatile ListAttribute<SumStatType, String> weight;
	public static volatile SingularAttribute<SumStatType, String> type;
}
