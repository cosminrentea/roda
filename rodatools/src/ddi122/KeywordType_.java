package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:29:43.227+0300")
@StaticMetamodel(KeywordType.class)
public class KeywordType_ {
	public static volatile SingularAttribute<KeywordType, Long> id_;
	public static volatile SingularAttribute<KeywordType, SubjectType> subjecttype;
	public static volatile ListAttribute<KeywordType, String> content;
	public static volatile SingularAttribute<KeywordType, String> id;
	public static volatile SingularAttribute<KeywordType, String> vocab;
	public static volatile SingularAttribute<KeywordType, String> vocabURI;
}
