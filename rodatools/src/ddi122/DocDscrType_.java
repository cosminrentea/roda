package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.212+0300")
@StaticMetamodel(DocDscrType.class)
public class DocDscrType_ {
	public static volatile SingularAttribute<DocDscrType, Long> id_;
	public static volatile SingularAttribute<DocDscrType, CodeBook> codebook;
	public static volatile SingularAttribute<DocDscrType, CitationType> citation;
	public static volatile ListAttribute<DocDscrType, DocSrcType> docSrc;
	public static volatile SingularAttribute<DocDscrType, String> id;
	public static volatile SingularAttribute<DocDscrType, String> xmlLang;
	public static volatile SingularAttribute<DocDscrType, String> source;
}
