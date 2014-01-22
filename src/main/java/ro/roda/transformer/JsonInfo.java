package ro.roda.transformer;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

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

	public static final DateTransformer DATE_TRANSFORMER2 = new DateTransformer("MM/dd/yyyy");

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

	protected static String[] getAuditedClasses(String packageName) {
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

}
