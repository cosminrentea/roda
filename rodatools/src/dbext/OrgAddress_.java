package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.269+0300")
@StaticMetamodel(OrgAddress.class)
public class OrgAddress_ {
	public static volatile SingularAttribute<OrgAddress, OrgAddressPK> id;
	public static volatile SingularAttribute<OrgAddress, Timestamp> dateend;
	public static volatile SingularAttribute<OrgAddress, Timestamp> datestart;
	public static volatile SingularAttribute<OrgAddress, Address> address;
	public static volatile SingularAttribute<OrgAddress, Org> org;
}
