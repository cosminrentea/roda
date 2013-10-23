package ro.roda.transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

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

}
