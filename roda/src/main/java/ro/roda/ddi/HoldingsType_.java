package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-04T14:36:09.111+0200")
@StaticMetamodel(HoldingsType.class)
public class HoldingsType_ {
	public static volatile SingularAttribute<HoldingsType, Long> id_;
	public static volatile SingularAttribute<HoldingsType, CitationType> citationtype;
	public static volatile SingularAttribute<HoldingsType, DocSrcType> docsrctype;
	public static volatile SingularAttribute<HoldingsType, String> content;
	public static volatile SingularAttribute<HoldingsType, String> id;
	public static volatile SingularAttribute<HoldingsType, String> uri;
}
