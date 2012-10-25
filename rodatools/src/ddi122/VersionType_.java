package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-22T18:11:12.259+0300")
@StaticMetamodel(VersionType.class)
public class VersionType_ {
	public static volatile SingularAttribute<VersionType, Long> id_;
	public static volatile ListAttribute<VersionType, String> content;
	public static volatile SingularAttribute<VersionType, String> id;
	public static volatile SingularAttribute<VersionType, String> xmlLang;
	public static volatile SingularAttribute<VersionType, String> source;
	public static volatile SingularAttribute<VersionType, String> date;
	public static volatile SingularAttribute<VersionType, String> type;
}
