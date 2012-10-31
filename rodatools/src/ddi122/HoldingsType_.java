package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:27:33.337+0300")
@StaticMetamodel(HoldingsType.class)
public class HoldingsType_ {
	public static volatile SingularAttribute<HoldingsType, Long> id_;
	public static volatile SingularAttribute<HoldingsType, CitationType> citationtype;
	public static volatile SingularAttribute<HoldingsType, DocSrcType> docsrctype;
	public static volatile ListAttribute<HoldingsType, String> content;
	public static volatile SingularAttribute<HoldingsType, String> id;
	public static volatile SingularAttribute<HoldingsType, String> uri;
}
