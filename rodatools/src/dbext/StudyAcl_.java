package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.412+0300")
@StaticMetamodel(StudyAcl.class)
public class StudyAcl_ {
	public static volatile SingularAttribute<StudyAcl, Integer> aroId;
	public static volatile SingularAttribute<StudyAcl, Integer> aroType;
	public static volatile SingularAttribute<StudyAcl, Boolean> delete;
	public static volatile SingularAttribute<StudyAcl, Boolean> modacl;
	public static volatile SingularAttribute<StudyAcl, Boolean> read;
	public static volatile SingularAttribute<StudyAcl, Boolean> update;
	public static volatile SingularAttribute<StudyAcl, Study> study;
}
