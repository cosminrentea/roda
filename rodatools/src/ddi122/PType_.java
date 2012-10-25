package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.511+0300")
@StaticMetamodel(PType.class)
public class PType_ {
	public static volatile SingularAttribute<PType, Long> id_;
	public static volatile SingularAttribute<PType, OthIdType> othidtype;
	public static volatile ListAttribute<PType, String> content;
	public static volatile SingularAttribute<PType, String> id;
	public static volatile SingularAttribute<PType, String> xmlLang;
	public static volatile SingularAttribute<PType, String> source;
}
