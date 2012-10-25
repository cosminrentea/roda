package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.381+0300")
@StaticMetamodel(CmsFile.class)
public class CmsFile_ {
	public static volatile SingularAttribute<CmsFile, Integer> id;
	public static volatile SingularAttribute<CmsFile, String> filename;
	public static volatile SingularAttribute<CmsFile, Integer> filesize;
	public static volatile SingularAttribute<CmsFile, String> label;
	public static volatile SingularAttribute<CmsFile, String> md5;
	public static volatile SingularAttribute<CmsFile, String> mimegroup;
	public static volatile SingularAttribute<CmsFile, String> mimesubgroup;
	public static volatile SingularAttribute<CmsFile, CmsFolder> cmsFolder;
}
