package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T17:30:59.054+0200")
@StaticMetamodel(CatStatType.class)
public class CatStatType_ {
	public static volatile SingularAttribute<CatStatType, Long> id_;
	public static volatile SingularAttribute<CatStatType, CatgryType> category;
	public static volatile SingularAttribute<CatStatType, String> content;
	public static volatile SingularAttribute<CatStatType, String> id;
	public static volatile SingularAttribute<CatStatType, String> type;
}
