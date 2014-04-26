package ro.roda.domainjson;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.audit.RodaRevisionEntity;
import flexjson.transformer.DateTransformer;

@Configurable
public class JsonInfo {

	// public abstract String toJson();

	private final Log log = LogFactory.getLog(this.getClass());

	public static final String CATALOG_TYPE = "C";

	public static final String SERIES_TYPE = "S";

	public static final String STUDY_TYPE = "St";

	public static final String SERIES_STUDY_TYPE = "Sts";

	public static final String YEAR_TYPE = "Y";

	public static final String MAIN_TYPE = "M";

	public static final DateTransformer DATE_TRANSFORMER = new DateTransformer("MM/dd/yyyy hh:mm:ss");

	public static final DateTransformer DATE_TRANSFORMER2 = new DateTransformer("yyyy-MM-dd");

	// protected static final String[] auditedClasses = {
	// "ro.roda.domain.CmsLayout", "ro.roda.domain.CmsLayoutGroup" };

	private Integer id;

	private String name;

	private String type;

	public JsonInfo() {
		super();
	}

	public JsonInfo(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public JsonInfo(Integer id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected static String[] findAuditedClasses(String packageName) {
		// Prepare.
		URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

		// Filter .class files.
		File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".class");
			}
		});

		Set<String> classesSet = new HashSet<String>();
		// Find classes.
		for (File file : files) {
			try {
				String className = file.getName().replaceAll(".class$", "");
				int indexOf$ = className.indexOf("$");
				if (indexOf$ > -1) {
					className = className.substring(0, indexOf$);
				}
				if (!className.endsWith("PK")) {
					classesSet.add(packageName + "." + className);
				}
			} catch (Exception e) {
				// TODO
			}
		}

		return classesSet.toArray(new String[] {});
	}

	protected static Set<AuditRow> findModifiedEntities(Class<?> auditedClass, RodaRevisionEntity revision) {
		// get the entities modified at the revision, for the
		// given class
		Set<AuditRow> auditRows = new HashSet<AuditRow>();
		try {
			AuditQuery queryEntities = revision.getAuditReader().createQuery()
					.forEntitiesModifiedAtRevision(auditedClass, revision.getId());
			List<?> resultEntities = queryEntities.getResultList();
			Iterator<?> iteratorEntities = resultEntities.iterator();

			Method getid = auditedClass.getMethod("getId");

			while (iteratorEntities.hasNext()) {
				Object object = iteratorEntities.next();

				Integer objectId = Integer.parseInt(getid.invoke(object).toString());

				Set<AuditField> auditedFields = new HashSet<AuditField>();
				Field[] classFields = auditedClass.getDeclaredFields();
				for (int j = 0; j < classFields.length; j++) {
					Field classField = classFields[j];
					// TODO get the fields correctly

					try {
						Method getAuditedField = auditedClass.getMethod("get"
								+ classField.getName().substring(0, 1).toUpperCase()
								+ classField.getName().substring(1));

						Object auditValue = getAuditedField.invoke(object);

						if (classField.getName().endsWith("Id")) {
							// The id's fields (corresponding to
							// foreign
							// keys in the data model) are actually
							// objects. As the integer values of the
							// id's are needed in the JSONs, we
							// retrieve
							// them as follows.
							auditedFields.add(new AuditField(classField.getName(), auditValue.getClass()
									.getMethod("getId").invoke(auditValue)));
						} else {
							auditedFields.add(new AuditField(classField.getName(), auditValue.toString()));
						}
					} catch (Exception e) {
						// TODO
					}
				}

				// get the revision type (insert, update or delete)
				AuditQuery queryRev = revision.getAuditReader().createQuery()
						.forRevisionsOfEntity(auditedClass, false, true).add(AuditEntity.id().eq(objectId));
				RevisionType revType = (RevisionType) ((Object[]) queryRev.getResultList().get(0))[2];
				auditRows.add(new AuditRow(objectId, revType != null ? revType.toString() : "", auditedFields.size(),
						auditedFields));
			}
		} catch (Exception e) {
			// TODO
		}

		return auditRows;
	}
}
