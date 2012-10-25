package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.341+0300")
@StaticMetamodel(InvalrngType.class)
public class InvalrngType_ {
	public static volatile SingularAttribute<InvalrngType, Long> id_;
	public static volatile SingularAttribute<InvalrngType, VarType> vartype;
	public static volatile ListAttribute<InvalrngType, RangeType> range;
	public static volatile ListAttribute<InvalrngType, ItemType> item;
	public static volatile SingularAttribute<InvalrngType, String> id;
	public static volatile SingularAttribute<InvalrngType, String> xmlLang;
	public static volatile SingularAttribute<InvalrngType, String> source;
}
