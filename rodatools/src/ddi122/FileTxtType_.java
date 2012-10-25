package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.248+0300")
@StaticMetamodel(FileTxtType.class)
public class FileTxtType_ {
	public static volatile SingularAttribute<FileTxtType, Long> id_;
	public static volatile SingularAttribute<FileTxtType, CatgryType> filetxttype;
	public static volatile SingularAttribute<FileTxtType, FileNameType> fileName;
	public static volatile SingularAttribute<FileTxtType, DimensnsType> dimensns;
	public static volatile SingularAttribute<FileTxtType, FileTypeType> fileType;
	public static volatile ListAttribute<FileTxtType, SoftwareType> software;
	public static volatile SingularAttribute<FileTxtType, VerStmtType> verStmt;
	public static volatile SingularAttribute<FileTxtType, String> id;
	public static volatile SingularAttribute<FileTxtType, String> xmlLang;
	public static volatile SingularAttribute<FileTxtType, String> source;
}
