package dbext;

import ddi122.CodeBook;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.249+0300")
@StaticMetamodel(Org.class)
public class Org_ {
	public static volatile SingularAttribute<Org, Integer> id;
	public static volatile SingularAttribute<Org, String> fullname;
	public static volatile SingularAttribute<Org, String> name;
	public static volatile ListAttribute<Org, Email> emails;
	public static volatile ListAttribute<Org, InstanceOrg> instanceOrgs;
	public static volatile ListAttribute<Org, CodeBook> Codebook;
	public static volatile ListAttribute<Org, Internet> internets;
	public static volatile SingularAttribute<Org, OrgPrefix> orgPrefix;
	public static volatile SingularAttribute<Org, OrgSufix> orgSufix;
	public static volatile SingularAttribute<Org, OrgAddress> orgAddress;
	public static volatile ListAttribute<Org, OrgRelation> orgRelations1;
	public static volatile ListAttribute<Org, OrgRelation> orgRelations2;
	public static volatile ListAttribute<Org, PersonOrg> personOrgs;
	public static volatile ListAttribute<Org, StudyOrg> studyOrgs;
}
