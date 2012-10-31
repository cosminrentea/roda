package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:26:12.084+0300")
@StaticMetamodel(TxtType.class)
public class TxtType_ {
	public static volatile SingularAttribute<TxtType, Long> id_;
	public static volatile SingularAttribute<TxtType, VarType> vartype;
	public static volatile SingularAttribute<TxtType, CatgryType> category;
	public static volatile ListAttribute<TxtType, String> content;
	public static volatile SingularAttribute<TxtType, String> id;
	public static volatile ListAttribute<TxtType, String> sdatrefs;
}
