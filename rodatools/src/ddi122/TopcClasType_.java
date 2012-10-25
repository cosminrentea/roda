package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.718+0300")
@StaticMetamodel(TopcClasType.class)
public class TopcClasType_ {
	public static volatile SingularAttribute<TopcClasType, Long> id_;
	public static volatile SingularAttribute<TopcClasType, SubjectType> subjecttype;
	public static volatile ListAttribute<TopcClasType, String> content;
	public static volatile SingularAttribute<TopcClasType, String> id;
	public static volatile SingularAttribute<TopcClasType, String> xmlLang;
	public static volatile SingularAttribute<TopcClasType, String> source;
	public static volatile SingularAttribute<TopcClasType, String> vocab;
	public static volatile SingularAttribute<TopcClasType, String> vocabURI;
}
