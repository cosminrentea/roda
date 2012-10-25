package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.750+0300")
@StaticMetamodel(CmsPage.class)
public class CmsPage_ {
	public static volatile SingularAttribute<CmsPage, Integer> id;
	public static volatile SingularAttribute<CmsPage, String> name;
	public static volatile SingularAttribute<CmsPage, Boolean> navigable;
	public static volatile SingularAttribute<CmsPage, Integer> owner;
	public static volatile SingularAttribute<CmsPage, Integer> pageType;
	public static volatile SingularAttribute<CmsPage, String> url;
	public static volatile SingularAttribute<CmsPage, Boolean> visible;
	public static volatile SingularAttribute<CmsPage, CmsLayout> cmsLayout;
	public static volatile ListAttribute<CmsPage, CmsPageContent> cmsPageContents;
}
