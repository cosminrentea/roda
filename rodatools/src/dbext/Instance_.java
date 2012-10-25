package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.981+0300")
@StaticMetamodel(Instance.class)
public class Instance_ {
	public static volatile SingularAttribute<Instance, Integer> id;
	public static volatile SingularAttribute<Instance, Timestamp> dateend;
	public static volatile SingularAttribute<Instance, Timestamp> datestart;
	public static volatile ListAttribute<Instance, Answer> answers;
	public static volatile SingularAttribute<Instance, DemoScope> demoScope;
	public static volatile ListAttribute<Instance, Document> documents;
	public static volatile SingularAttribute<Instance, Study> study;
	public static volatile SingularAttribute<Instance, UnitAnalysi> unitAnalysi;
	public static volatile ListAttribute<Instance, InstanceAcl> instanceAcls;
	public static volatile ListAttribute<Instance, InstanceDescr> instanceDescrs;
	public static volatile ListAttribute<Instance, InstanceOrg> instanceOrgs;
	public static volatile ListAttribute<Instance, InstancePerson> instancePersons;
	public static volatile ListAttribute<Instance, Methodology> methodologies;
	public static volatile ListAttribute<Instance, Variable> variables;
}
