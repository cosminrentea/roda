package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:01:33.700+0300")
@StaticMetamodel(SoftwareType.class)
public class SoftwareType_ {
	public static volatile SingularAttribute<SoftwareType, Long> id_;
	public static volatile SingularAttribute<SoftwareType, ProdStmtType> prodstmttype;
	public static volatile SingularAttribute<SoftwareType, SoftwareType> softwaretype;
	public static volatile ListAttribute<SoftwareType, String> content;
	public static volatile SingularAttribute<SoftwareType, String> id;
	public static volatile SingularAttribute<SoftwareType, String> date;
	public static volatile SingularAttribute<SoftwareType, String> version;
}
