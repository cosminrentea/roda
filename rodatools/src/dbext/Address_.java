package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.570+0300")
@StaticMetamodel(Address.class)
public class Address_ {
	public static volatile SingularAttribute<Address, Integer> id;
	public static volatile SingularAttribute<Address, String> address1;
	public static volatile SingularAttribute<Address, String> address2;
	public static volatile SingularAttribute<Address, Integer> entityId;
	public static volatile SingularAttribute<Address, Integer> entityType;
	public static volatile SingularAttribute<Address, String> postalCode;
	public static volatile SingularAttribute<Address, String> sector;
	public static volatile SingularAttribute<Address, City> city;
	public static volatile SingularAttribute<Address, Country> country;
	public static volatile ListAttribute<Address, OrgAddress> orgAddresses;
	public static volatile ListAttribute<Address, PersonAddress> personAddresses;
}
