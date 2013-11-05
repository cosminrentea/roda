package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T17:35:25.788+0200")
@StaticMetamodel(QstnType.class)
public class QstnType_ {
	public static volatile SingularAttribute<QstnType, Long> id_;
	public static volatile SingularAttribute<QstnType, VarType> vartype;
	public static volatile ListAttribute<QstnType, QstnLitType> qstnLit;
	public static volatile SingularAttribute<QstnType, String> id;
	public static volatile SingularAttribute<QstnType, String> seqNo;
}
