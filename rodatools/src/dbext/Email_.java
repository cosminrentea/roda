package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.797+0300")
@StaticMetamodel(Email.class)
public class Email_ {
	public static volatile SingularAttribute<Email, Integer> id;
	public static volatile SingularAttribute<Email, String> email;
	public static volatile SingularAttribute<Email, Boolean> ismain;
	public static volatile SingularAttribute<Email, Org> org;
	public static volatile SingularAttribute<Email, Person> person;
}
