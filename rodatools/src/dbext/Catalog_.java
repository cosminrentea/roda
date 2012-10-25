package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.271+0300")
@StaticMetamodel(Catalog.class)
public class Catalog_ {
	public static volatile SingularAttribute<Catalog, Integer> id;
	public static volatile SingularAttribute<Catalog, Timestamp> added;
	public static volatile SingularAttribute<Catalog, String> name;
	public static volatile SingularAttribute<Catalog, Integer> owner;
	public static volatile SingularAttribute<Catalog, Integer> parent;
	public static volatile ListAttribute<Catalog, CatalogAcl> catalogAcls;
	public static volatile ListAttribute<Catalog, CatalogStudy> catalogStudies;
}
