package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.766+0300")
@StaticMetamodel(CmsSnippet.class)
public class CmsSnippet_ {
	public static volatile SingularAttribute<CmsSnippet, Integer> id;
	public static volatile SingularAttribute<CmsSnippet, String> name;
	public static volatile SingularAttribute<CmsSnippet, String> snippetContent;
	public static volatile SingularAttribute<CmsSnippet, CmsSnippetGroup> cmsSnippetGroup;
}
