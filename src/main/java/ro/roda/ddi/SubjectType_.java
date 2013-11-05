package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:02:32.073+0300")
@StaticMetamodel(SubjectType.class)
public class SubjectType_ {
	public static volatile SingularAttribute<SubjectType, Long> id_;
	public static volatile ListAttribute<SubjectType, KeywordType> keyword;
	public static volatile ListAttribute<SubjectType, TopcClasType> topcClas;
	public static volatile SingularAttribute<SubjectType, String> id;
}
