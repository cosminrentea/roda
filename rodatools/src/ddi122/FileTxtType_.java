package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.opendatafoundation.data.spss.SPSSFile;

@Generated(value="Dali", date="2012-11-04T14:47:19.809+0200")
@StaticMetamodel(FileTxtType.class)
public class FileTxtType_ {
	public static volatile SingularAttribute<FileTxtType, Long> id_;
	public static volatile SingularAttribute<FileTxtType, SPSSFile> spssfile;
	public static volatile SingularAttribute<FileTxtType, FileDscrType> filedscrtype;
	public static volatile SingularAttribute<FileTxtType, FileNameType> fileName;
	public static volatile SingularAttribute<FileTxtType, DimensnsType> dimensns;
	public static volatile SingularAttribute<FileTxtType, FileTypeType> fileType;
	public static volatile ListAttribute<FileTxtType, SoftwareType> software;
	public static volatile SingularAttribute<FileTxtType, VerStmtType> verStmt;
	public static volatile SingularAttribute<FileTxtType, String> id;
}
