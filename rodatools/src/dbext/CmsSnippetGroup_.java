package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.777+0300")
@StaticMetamodel(CmsSnippetGroup.class)
public class CmsSnippetGroup_ {
	public static volatile SingularAttribute<CmsSnippetGroup, Integer> id;
	public static volatile SingularAttribute<CmsSnippetGroup, String> description;
	public static volatile SingularAttribute<CmsSnippetGroup, String> name;
	public static volatile SingularAttribute<CmsSnippetGroup, Integer> parent;
	public static volatile ListAttribute<CmsSnippetGroup, CmsSnippet> cmsSnippets;
}
