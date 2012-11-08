package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T17:29:15.070+0200")
@StaticMetamodel(CatgryType.class)
public class CatgryType_ {
	public static volatile SingularAttribute<CatgryType, Long> id_;
	public static volatile SingularAttribute<CatgryType, VarType> vartype;
	public static volatile SingularAttribute<CatgryType, CatValuType> catValu;
	public static volatile ListAttribute<CatgryType, LablType> labl;
	public static volatile ListAttribute<CatgryType, TxtType> txt;
	public static volatile ListAttribute<CatgryType, CatStatType> catStat;
	public static volatile ListAttribute<CatgryType, CatgryType> catgry;
	public static volatile SingularAttribute<CatgryType, String> id;
	public static volatile SingularAttribute<CatgryType, String> missing;
}
