package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.960+0300")
@StaticMetamodel(StudyOrgAsoc.class)
public class StudyOrgAsoc_ {
	public static volatile SingularAttribute<StudyOrgAsoc, Integer> id;
	public static volatile SingularAttribute<StudyOrgAsoc, Long> asocDescription;
	public static volatile SingularAttribute<StudyOrgAsoc, String> asocName;
	public static volatile SingularAttribute<StudyOrgAsoc, String> assocDetails;
	public static volatile ListAttribute<StudyOrgAsoc, StudyOrg> studyOrgs;
}
