package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.506+0300")
@StaticMetamodel(ProducerType.class)
public class ProducerType_ {
	public static volatile SingularAttribute<ProducerType, Long> id_;
	public static volatile SingularAttribute<ProducerType, ProdStmtType> prodstmttype;
	public static volatile ListAttribute<ProducerType, String> content;
	public static volatile SingularAttribute<ProducerType, String> id;
	public static volatile SingularAttribute<ProducerType, String> xmlLang;
	public static volatile SingularAttribute<ProducerType, String> source;
	public static volatile SingularAttribute<ProducerType, String> abbr;
	public static volatile SingularAttribute<ProducerType, String> affiliation;
	public static volatile SingularAttribute<ProducerType, String> role;
}
