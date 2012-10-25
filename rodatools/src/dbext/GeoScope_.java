package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:15.068+0300")
@StaticMetamodel(GeoScope.class)
public class GeoScope_ {
	public static volatile SingularAttribute<GeoScope, Integer> id;
	public static volatile SingularAttribute<GeoScope, String> description;
	public static volatile SingularAttribute<GeoScope, Integer> geoSliceId;
	public static volatile ListAttribute<GeoScope, DemoSlice> demoSlices;
	public static volatile ListAttribute<GeoScope, GeoSlice> geoSlices;
}
