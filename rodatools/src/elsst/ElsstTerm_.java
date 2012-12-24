package elsst;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-08T15:51:29.535+0200")
@StaticMetamodel(ElsstTerm.class)
public class ElsstTerm_ {
	public static volatile SingularAttribute<ElsstTerm, Long> id_;
	public static volatile MapAttribute<ElsstTerm, ElsstLanguage, ElsstTerm> translatedTerm;
	public static volatile SingularAttribute<ElsstTerm, String> name;
	public static volatile SingularAttribute<ElsstTerm, String> scopeNote;
	public static volatile SingularAttribute<ElsstTerm, ElsstTerm> preferredTerm;
	public static volatile SingularAttribute<ElsstTerm, ElsstTerm> broaderTerm;
	public static volatile ListAttribute<ElsstTerm, ElsstTerm> relatedTerm;
}
