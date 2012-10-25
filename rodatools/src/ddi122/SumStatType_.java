package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.695+0300")
@StaticMetamodel(SumStatType.class)
public class SumStatType_ {
	public static volatile SingularAttribute<SumStatType, Long> id_;
	public static volatile SingularAttribute<SumStatType, VarType> vartype;
	public static volatile ListAttribute<SumStatType, String> content;
	public static volatile SingularAttribute<SumStatType, String> id;
	public static volatile SingularAttribute<SumStatType, String> xmlLang;
	public static volatile SingularAttribute<SumStatType, String> source;
	public static volatile SingularAttribute<SumStatType, String> wgtd;
	public static volatile ListAttribute<SumStatType, String> wgtVar;
	public static volatile ListAttribute<SumStatType, String> weight;
	public static volatile SingularAttribute<SumStatType, String> type;
}
