package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.344+0300")
@StaticMetamodel(OrgRelation.class)
public class OrgRelation_ {
	public static volatile SingularAttribute<OrgRelation, OrgRelationPK> id;
	public static volatile SingularAttribute<OrgRelation, Timestamp> dateend;
	public static volatile SingularAttribute<OrgRelation, Timestamp> datestart;
	public static volatile SingularAttribute<OrgRelation, String> details;
	public static volatile SingularAttribute<OrgRelation, Org> org1;
	public static volatile SingularAttribute<OrgRelation, Org> org2;
	public static volatile SingularAttribute<OrgRelation, OrgRelationType> orgRelationType;
}
