package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.452+0300")
@StaticMetamodel(OthrStdyMatType.class)
public class OthrStdyMatType_ {
	public static volatile SingularAttribute<OthrStdyMatType, Long> id_;
	public static volatile SingularAttribute<OthrStdyMatType, StdyDscrType> stdydscrtype;
	public static volatile ListAttribute<OthrStdyMatType, RelMatType> relMat;
	public static volatile SingularAttribute<OthrStdyMatType, String> id;
	public static volatile SingularAttribute<OthrStdyMatType, String> xmlLang;
	public static volatile SingularAttribute<OthrStdyMatType, String> source;
}
