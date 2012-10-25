package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.972+0300")
@StaticMetamodel(GeoSlice.class)
public class GeoSlice_ {
	public static volatile SingularAttribute<GeoSlice, Integer> id;
	public static volatile SingularAttribute<GeoSlice, Integer> city;
	public static volatile SingularAttribute<GeoSlice, Integer> country;
	public static volatile SingularAttribute<GeoSlice, GeoScope> geoScope;
}
