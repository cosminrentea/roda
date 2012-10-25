package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.425+0300")
@StaticMetamodel(CmsFolder.class)
public class CmsFolder_ {
	public static volatile SingularAttribute<CmsFolder, Integer> id;
	public static volatile SingularAttribute<CmsFolder, String> description;
	public static volatile SingularAttribute<CmsFolder, String> name;
	public static volatile SingularAttribute<CmsFolder, Integer> parent;
	public static volatile ListAttribute<CmsFolder, CmsFile> cmsFiles;
}
