package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.849+0300")
@StaticMetamodel(Country.class)
public class Country_ {
	public static volatile SingularAttribute<Country, Integer> id;
	public static volatile SingularAttribute<Country, String> name;
	public static volatile ListAttribute<Country, Address> addresses;
	public static volatile ListAttribute<Country, City> cities;
}
