package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T17:33:01.800+0200")
@StaticMetamodel(FileDscrType.class)
public class FileDscrType_ {
	public static volatile SingularAttribute<FileDscrType, Long> id_;
	public static volatile SingularAttribute<FileDscrType, CodeBook> codebook;
	public static volatile ListAttribute<FileDscrType, FileTxtType> fileTxt;
	public static volatile SingularAttribute<FileDscrType, String> id;
	public static volatile SingularAttribute<FileDscrType, String> uri;
}
