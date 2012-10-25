package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.401+0300")
@StaticMetamodel(Study.class)
public class Study_ {
	public static volatile SingularAttribute<Study, Integer> id;
	public static volatile SingularAttribute<Study, Timestamp> dateend;
	public static volatile SingularAttribute<Study, Timestamp> datestart;
	public static volatile ListAttribute<Study, CatalogStudy> catalogStudies;
	public static volatile ListAttribute<Study, Instance> instances;
	public static volatile ListAttribute<Study, StudyAcl> studyAcls;
	public static volatile ListAttribute<Study, StudyDescr> studyDescrs;
	public static volatile ListAttribute<Study, Document> documents;
	public static volatile ListAttribute<Study, StudyOrg> studyOrgs;
	public static volatile ListAttribute<Study, StudyPerson> studyPersons;
}
