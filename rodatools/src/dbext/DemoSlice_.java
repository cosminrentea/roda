package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.695+0300")
@StaticMetamodel(DemoSlice.class)
public class DemoSlice_ {
	public static volatile SingularAttribute<DemoSlice, Integer> id;
	public static volatile SingularAttribute<DemoSlice, Integer> ageMax;
	public static volatile SingularAttribute<DemoSlice, Integer> ageMin;
	public static volatile SingularAttribute<DemoSlice, Integer> sex;
	public static volatile SingularAttribute<DemoSlice, DemoScope> demoScope;
	public static volatile SingularAttribute<DemoSlice, GeoScope> geoScope;
}
