package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.667+0300")
@StaticMetamodel(CatalogStudy.class)
public class CatalogStudy_ {
	public static volatile SingularAttribute<CatalogStudy, CatalogStudyPK> id;
	public static volatile SingularAttribute<CatalogStudy, Integer> addedBy;
	public static volatile SingularAttribute<CatalogStudy, Catalog> catalog;
	public static volatile SingularAttribute<CatalogStudy, Study> study;
}
