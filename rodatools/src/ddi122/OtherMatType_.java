package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:55:01.303+0300")
@StaticMetamodel(OtherMatType.class)
public class OtherMatType_ {
	public static volatile SingularAttribute<OtherMatType, Long> id_;
	public static volatile SingularAttribute<OtherMatType, CodeBook> codebook;
	public static volatile ListAttribute<OtherMatType, OtherMatType> otherMat;
	public static volatile SingularAttribute<OtherMatType, OtherMatType> othermattype;
	public static volatile ListAttribute<OtherMatType, LablType> labl;
	public static volatile SingularAttribute<OtherMatType, TxtType> txt;
	public static volatile SingularAttribute<OtherMatType, CitationType> citation;
	public static volatile SingularAttribute<OtherMatType, String> id;
	public static volatile SingularAttribute<OtherMatType, String> type;
	public static volatile SingularAttribute<OtherMatType, String> level;
	public static volatile SingularAttribute<OtherMatType, String> uri;
}
