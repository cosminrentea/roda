package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:57:02.565+0300")
@StaticMetamodel(OthrStdyMatType.class)
public class OthrStdyMatType_ {
	public static volatile SingularAttribute<OthrStdyMatType, Long> id_;
	public static volatile SingularAttribute<OthrStdyMatType, StdyDscrType> stdydscrtype;
	public static volatile ListAttribute<OthrStdyMatType, RelMatType> relMat;
	public static volatile SingularAttribute<OthrStdyMatType, String> id;
}
