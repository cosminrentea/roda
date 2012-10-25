package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.824+0300")
@StaticMetamodel(StudiuPersonAsoc.class)
public class StudiuPersonAsoc_ {
	public static volatile SingularAttribute<StudiuPersonAsoc, Integer> id;
	public static volatile SingularAttribute<StudiuPersonAsoc, String> asocDescription;
	public static volatile SingularAttribute<StudiuPersonAsoc, String> asocName;
	public static volatile ListAttribute<StudiuPersonAsoc, StudyPerson> studyPersons;
}
