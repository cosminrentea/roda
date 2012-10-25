package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:15.213+0300")
@StaticMetamodel(InstanceOrgAssoc.class)
public class InstanceOrgAssoc_ {
	public static volatile SingularAttribute<InstanceOrgAssoc, Integer> id;
	public static volatile SingularAttribute<InstanceOrgAssoc, String> assocDescription;
	public static volatile SingularAttribute<InstanceOrgAssoc, String> assocName;
	public static volatile ListAttribute<InstanceOrgAssoc, InstanceOrg> instanceOrgs;
}
