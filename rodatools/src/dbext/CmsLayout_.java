package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.732+0300")
@StaticMetamodel(CmsLayout.class)
public class CmsLayout_ {
	public static volatile SingularAttribute<CmsLayout, Integer> id;
	public static volatile SingularAttribute<CmsLayout, String> layoutContent;
	public static volatile SingularAttribute<CmsLayout, String> name;
	public static volatile SingularAttribute<CmsLayout, CmsLayoutGroup> cmsLayoutGroup;
	public static volatile ListAttribute<CmsLayout, CmsPage> cmsPages;
}
