package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.737+0300")
@StaticMetamodel(UniverseType.class)
public class UniverseType_ {
	public static volatile SingularAttribute<UniverseType, Long> id_;
	public static volatile SingularAttribute<UniverseType, VarType> vartype;
	public static volatile SingularAttribute<UniverseType, SumDscrType> sumdscrtype;
	public static volatile ListAttribute<UniverseType, String> content;
	public static volatile SingularAttribute<UniverseType, String> id;
	public static volatile SingularAttribute<UniverseType, String> xmlLang;
	public static volatile SingularAttribute<UniverseType, String> source;
	public static volatile SingularAttribute<UniverseType, String> level;
	public static volatile SingularAttribute<UniverseType, String> clusion;
}
