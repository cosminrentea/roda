package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.314+0300")
@StaticMetamodel(OrgPrefix.class)
public class OrgPrefix_ {
	public static volatile SingularAttribute<OrgPrefix, Integer> id;
	public static volatile SingularAttribute<OrgPrefix, String> description;
	public static volatile SingularAttribute<OrgPrefix, String> name;
	public static volatile ListAttribute<OrgPrefix, Org> orgs;
}
