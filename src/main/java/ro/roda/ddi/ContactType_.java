package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-04T14:36:08.853+0200")
@StaticMetamodel(ContactType.class)
public class ContactType_ {
	public static volatile SingularAttribute<ContactType, Long> id_;
	public static volatile SingularAttribute<ContactType, UseStmtType> usestmttype;
	public static volatile SingularAttribute<ContactType, DistStmtType> diststmttype;
	public static volatile SingularAttribute<ContactType, String> content;
	public static volatile SingularAttribute<ContactType, String> id;
	public static volatile SingularAttribute<ContactType, String> affiliation;
	public static volatile SingularAttribute<ContactType, String> uri;
	public static volatile SingularAttribute<ContactType, String> email;
}
