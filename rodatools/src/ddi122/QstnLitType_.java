package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:58:46.304+0300")
@StaticMetamodel(QstnLitType.class)
public class QstnLitType_ {
	public static volatile SingularAttribute<QstnLitType, Long> id_;
	public static volatile SingularAttribute<QstnLitType, QstnType> qstntype;
	public static volatile ListAttribute<QstnLitType, String> content;
	public static volatile SingularAttribute<QstnLitType, String> id;
	public static volatile ListAttribute<QstnLitType, String> sdatrefs;
}
