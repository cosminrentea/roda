package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:14.788+0300")
@StaticMetamodel(Concept.class)
public class Concept_ {
	public static volatile SingularAttribute<Concept, Integer> id;
	public static volatile SingularAttribute<Concept, String> description;
	public static volatile SingularAttribute<Concept, String> name;
	public static volatile ListAttribute<Concept, ConceptVariable> conceptVariables;
}
