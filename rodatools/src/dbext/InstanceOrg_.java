package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.057+0300")
@StaticMetamodel(InstanceOrg.class)
public class InstanceOrg_ {
	public static volatile SingularAttribute<InstanceOrg, InstanceOrgPK> id;
	public static volatile SingularAttribute<InstanceOrg, String> assocDetails;
	public static volatile SingularAttribute<InstanceOrg, Instance> instance;
	public static volatile SingularAttribute<InstanceOrg, InstanceOrgAssoc> instanceOrgAssoc;
	public static volatile SingularAttribute<InstanceOrg, Org> org;
}
