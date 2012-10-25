package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.968+0300")
@StaticMetamodel(DocumentType.class)
public class DocumentType_ {
	public static volatile SingularAttribute<DocumentType, Integer> id;
	public static volatile SingularAttribute<DocumentType, String> name;
	public static volatile ListAttribute<DocumentType, Document> documents;
}
