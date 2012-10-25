package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.467+0300")
@StaticMetamodel(CmsLayoutGroup.class)
public class CmsLayoutGroup_ {
	public static volatile SingularAttribute<CmsLayoutGroup, Integer> id;
	public static volatile SingularAttribute<CmsLayoutGroup, String> description;
	public static volatile SingularAttribute<CmsLayoutGroup, String> name;
	public static volatile SingularAttribute<CmsLayoutGroup, Integer> parent;
	public static volatile ListAttribute<CmsLayoutGroup, CmsLayout> cmsLayouts;
}
