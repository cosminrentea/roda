package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:39:11.288+0300")
@StaticMetamodel(RangeType.class)
public class RangeType_ {
	public static volatile SingularAttribute<RangeType, Long> id_;
	public static volatile SingularAttribute<RangeType, ValrngType> valrngtype;
	public static volatile SingularAttribute<RangeType, InvalrngType> invalrngtype;
	public static volatile SingularAttribute<RangeType, String> id;
	public static volatile SingularAttribute<RangeType, String> units;
	public static volatile SingularAttribute<RangeType, String> min;
	public static volatile SingularAttribute<RangeType, String> max;
}
