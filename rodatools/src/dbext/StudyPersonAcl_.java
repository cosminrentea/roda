package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.483+0300")
@StaticMetamodel(StudyPersonAcl.class)
public class StudyPersonAcl_ {
	public static volatile SingularAttribute<StudyPersonAcl, Integer> aroId;
	public static volatile SingularAttribute<StudyPersonAcl, Integer> aroType;
	public static volatile SingularAttribute<StudyPersonAcl, Boolean> delete;
	public static volatile SingularAttribute<StudyPersonAcl, Boolean> modacl;
	public static volatile SingularAttribute<StudyPersonAcl, Boolean> read;
	public static volatile SingularAttribute<StudyPersonAcl, Boolean> update;
	public static volatile SingularAttribute<StudyPersonAcl, StudyPerson> studyPerson;
}
