package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:59:41.323+0300")
@StaticMetamodel(RelMatType.class)
public class RelMatType_ {
	public static volatile SingularAttribute<RelMatType, Long> id_;
	public static volatile SingularAttribute<RelMatType, OthrStdyMatType> othrstdymattype;
	public static volatile ListAttribute<RelMatType, CitationType> citation;
	public static volatile SingularAttribute<RelMatType, String> id;
	public static volatile SingularAttribute<RelMatType, String> callno;
	public static volatile SingularAttribute<RelMatType, String> label;
	public static volatile SingularAttribute<RelMatType, String> media;
	public static volatile SingularAttribute<RelMatType, String> type;
}
