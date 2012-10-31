package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:58:27.230+0300")
@StaticMetamodel(PType.class)
public class PType_ {
	public static volatile SingularAttribute<PType, Long> id_;
	public static volatile SingularAttribute<PType, OthIdType> othidtype;
	public static volatile ListAttribute<PType, String> content;
	public static volatile SingularAttribute<PType, String> id;
}
