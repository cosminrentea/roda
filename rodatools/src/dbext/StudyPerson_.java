package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.464+0300")
@StaticMetamodel(StudyPerson.class)
public class StudyPerson_ {
	public static volatile SingularAttribute<StudyPerson, Integer> id;
	public static volatile SingularAttribute<StudyPerson, Person> person;
	public static volatile SingularAttribute<StudyPerson, StudiuPersonAsoc> studiuPersonAsoc;
	public static volatile SingularAttribute<StudyPerson, Study> study;
	public static volatile ListAttribute<StudyPerson, StudyPersonAcl> studyPersonAcls;
}
