package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:47:40.045+0300")
@StaticMetamodel(FileDscrType.class)
public class FileDscrType_ {
	public static volatile SingularAttribute<FileDscrType, Long> id_;
	public static volatile SingularAttribute<FileDscrType, CodeBook> codebook;
	public static volatile ListAttribute<FileDscrType, FileTxtType> fileTxt;
	public static volatile SingularAttribute<FileDscrType, String> id;
	public static volatile SingularAttribute<FileDscrType, String> uri;
	public static volatile ListAttribute<FileDscrType, String> sdatrefs;
	public static volatile ListAttribute<FileDscrType, String> methrefs;
	public static volatile ListAttribute<FileDscrType, String> pubrefs;
	public static volatile ListAttribute<FileDscrType, String> access;
}
