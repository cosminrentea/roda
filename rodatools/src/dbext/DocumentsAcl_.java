package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.959+0300")
@StaticMetamodel(DocumentsAcl.class)
public class DocumentsAcl_ {
	public static volatile SingularAttribute<DocumentsAcl, Integer> aroId;
	public static volatile SingularAttribute<DocumentsAcl, Integer> aroType;
	public static volatile SingularAttribute<DocumentsAcl, Boolean> delete;
	public static volatile SingularAttribute<DocumentsAcl, Boolean> read;
	public static volatile SingularAttribute<DocumentsAcl, Boolean> update;
	public static volatile SingularAttribute<DocumentsAcl, Document> document;
}
