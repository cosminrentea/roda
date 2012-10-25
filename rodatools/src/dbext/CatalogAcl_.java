package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.661+0300")
@StaticMetamodel(CatalogAcl.class)
public class CatalogAcl_ {
	public static volatile SingularAttribute<CatalogAcl, Integer> aroId;
	public static volatile SingularAttribute<CatalogAcl, Integer> aroType;
	public static volatile SingularAttribute<CatalogAcl, Boolean> delete;
	public static volatile SingularAttribute<CatalogAcl, Boolean> modacl;
	public static volatile SingularAttribute<CatalogAcl, Boolean> read;
	public static volatile SingularAttribute<CatalogAcl, Boolean> update;
	public static volatile SingularAttribute<CatalogAcl, Catalog> catalog;
}
