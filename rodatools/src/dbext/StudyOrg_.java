package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.431+0300")
@StaticMetamodel(StudyOrg.class)
public class StudyOrg_ {
	public static volatile SingularAttribute<StudyOrg, Integer> id;
	public static volatile SingularAttribute<StudyOrg, Org> org;
	public static volatile SingularAttribute<StudyOrg, Study> study;
	public static volatile SingularAttribute<StudyOrg, StudyOrgAsoc> studyOrgAsoc;
	public static volatile ListAttribute<StudyOrg, StudyOrgAcl> studyOrgAcls;
}
