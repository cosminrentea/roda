package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.094+0300")
@StaticMetamodel(OrgSufix.class)
public class OrgSufix_ {
	public static volatile SingularAttribute<OrgSufix, Integer> id;
	public static volatile SingularAttribute<OrgSufix, String> description;
	public static volatile SingularAttribute<OrgSufix, String> name;
	public static volatile ListAttribute<OrgSufix, Org> orgs;
}
