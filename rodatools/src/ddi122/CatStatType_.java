package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:23:02.675+0300")
@StaticMetamodel(CatStatType.class)
public class CatStatType_ {
	public static volatile SingularAttribute<CatStatType, Long> id_;
	public static volatile SingularAttribute<CatStatType, CatgryType> category;
	public static volatile ListAttribute<CatStatType, String> content;
	public static volatile SingularAttribute<CatStatType, String> id;
	public static volatile SingularAttribute<CatStatType, String> type;
	public static volatile ListAttribute<CatStatType, String> methrefs;
	public static volatile ListAttribute<CatStatType, String> wgtVar;
	public static volatile ListAttribute<CatStatType, String> weight;
	public static volatile ListAttribute<CatStatType, String> sdatrefs;
}
