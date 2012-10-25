package ddi122;

import dbext.Org;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T16:03:02.758+0300")
@StaticMetamodel(CodeBook.class)
public class CodeBook_ {
	public static volatile SingularAttribute<CodeBook, Long> id_;
	public static volatile SingularAttribute<CodeBook, Org> org;
	public static volatile ListAttribute<CodeBook, DocDscrType> docDscr;
	public static volatile ListAttribute<CodeBook, StdyDscrType> stdyDscr;
	public static volatile ListAttribute<CodeBook, FileDscrType> fileDscr;
	public static volatile ListAttribute<CodeBook, DataDscrType> dataDscr;
	public static volatile ListAttribute<CodeBook, OtherMatType> otherMat;
	public static volatile SingularAttribute<CodeBook, String> id;
	public static volatile SingularAttribute<CodeBook, String> xmlLang;
	public static volatile SingularAttribute<CodeBook, String> source;
	public static volatile SingularAttribute<CodeBook, String> version;
}
