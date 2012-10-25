package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.440+0300")
@StaticMetamodel(StudyOrgAcl.class)
public class StudyOrgAcl_ {
	public static volatile SingularAttribute<StudyOrgAcl, Integer> aroId;
	public static volatile SingularAttribute<StudyOrgAcl, Integer> aroType;
	public static volatile SingularAttribute<StudyOrgAcl, Boolean> delete;
	public static volatile SingularAttribute<StudyOrgAcl, Boolean> modacl;
	public static volatile SingularAttribute<StudyOrgAcl, Boolean> read;
	public static volatile SingularAttribute<StudyOrgAcl, Integer> studyId;
	public static volatile SingularAttribute<StudyOrgAcl, Boolean> update;
	public static volatile SingularAttribute<StudyOrgAcl, StudyOrg> studyOrg;
}
