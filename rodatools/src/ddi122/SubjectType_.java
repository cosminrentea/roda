package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:43.161+0300")
@StaticMetamodel(SubjectType.class)
public class SubjectType_ {
	public static volatile SingularAttribute<SubjectType, Long> id_;
	public static volatile ListAttribute<SubjectType, KeywordType> keyword;
	public static volatile ListAttribute<SubjectType, TopcClasType> topcClas;
	public static volatile SingularAttribute<SubjectType, String> id;
	public static volatile SingularAttribute<SubjectType, String> xmlLang;
	public static volatile SingularAttribute<SubjectType, String> source;
}
