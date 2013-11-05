package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:46:22.392+0300")
@StaticMetamodel(DocDscrType.class)
public class DocDscrType_ {
	public static volatile SingularAttribute<DocDscrType, Long> id_;
	public static volatile SingularAttribute<DocDscrType, CodeBook> codebook;
	public static volatile SingularAttribute<DocDscrType, CitationType> citation;
	public static volatile ListAttribute<DocDscrType, DocSrcType> docSrc;
	public static volatile SingularAttribute<DocDscrType, String> id;
}
