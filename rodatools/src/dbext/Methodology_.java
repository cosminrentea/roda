package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:15.322+0300")
@StaticMetamodel(Methodology.class)
public class Methodology_ {
	public static volatile SingularAttribute<Methodology, Integer> id;
	public static volatile SingularAttribute<Methodology, String> modeCollection;
	public static volatile SingularAttribute<Methodology, String> samplingProcedure;
	public static volatile SingularAttribute<Methodology, String> timeMethod;
	public static volatile SingularAttribute<Methodology, String> weighting;
	public static volatile SingularAttribute<Methodology, Instance> instance;
}
