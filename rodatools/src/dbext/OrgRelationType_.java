package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.078+0300")
@StaticMetamodel(OrgRelationType.class)
public class OrgRelationType_ {
	public static volatile SingularAttribute<OrgRelationType, Integer> id;
	public static volatile SingularAttribute<OrgRelationType, String> name;
	public static volatile ListAttribute<OrgRelationType, OrgRelation> orgRelations;
}
