package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.377+0300")
@StaticMetamodel(LablType.class)
public class LablType_ {
	public static volatile SingularAttribute<LablType, Long> id_;
	public static volatile SingularAttribute<LablType, VarType> vartype;
	public static volatile SingularAttribute<LablType, CatgryType> category;
	public static volatile SingularAttribute<LablType, OtherMatType> othermattype;
	public static volatile ListAttribute<LablType, String> content;
	public static volatile SingularAttribute<LablType, String> id;
	public static volatile SingularAttribute<LablType, String> xmlLang;
	public static volatile SingularAttribute<LablType, String> source;
	public static volatile SingularAttribute<LablType, String> level;
	public static volatile SingularAttribute<LablType, String> vendor;
}
