package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.951+0300")
@StaticMetamodel(Document.class)
public class Document_ {
	public static volatile SingularAttribute<Document, Integer> id;
	public static volatile SingularAttribute<Document, String> description;
	public static volatile SingularAttribute<Document, String> filename;
	public static volatile SingularAttribute<Document, Integer> filesize;
	public static volatile SingularAttribute<Document, String> mimetype;
	public static volatile SingularAttribute<Document, String> title;
	public static volatile SingularAttribute<Document, DocumentType> documentType;
	public static volatile ListAttribute<Document, DocumentsAcl> documentsAcls;
	public static volatile ListAttribute<Document, Instance> instances;
	public static volatile ListAttribute<Document, Study> studies;
}
