package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2012-11-04T14:36:09.521+0200")
@StaticMetamodel(TxtType.class)
public class TxtType_ {
	public static volatile SingularAttribute<TxtType, Long> id_;
	public static volatile SingularAttribute<TxtType, VarType> vartype;
	public static volatile SingularAttribute<TxtType, CatgryType> catgry;
	public static volatile SingularAttribute<TxtType, String> content;
	public static volatile SingularAttribute<TxtType, String> id;
	// public static volatile ListAttribute<TxtType, String> sdatrefs;
}
