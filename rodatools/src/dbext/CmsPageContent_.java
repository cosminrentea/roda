package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.756+0300")
@StaticMetamodel(CmsPageContent.class)
public class CmsPageContent_ {
	public static volatile SingularAttribute<CmsPageContent, Integer> id;
	public static volatile SingularAttribute<CmsPageContent, String> contentText;
	public static volatile SingularAttribute<CmsPageContent, String> contentTitle;
	public static volatile SingularAttribute<CmsPageContent, String> name;
	public static volatile SingularAttribute<CmsPageContent, Integer> sqnumber;
	public static volatile SingularAttribute<CmsPageContent, CmsPage> cmsPage;
}
