package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeCode;
import ro.roda.domain.QuestionTypeNumeric;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class ResponseInfo extends JsonInfo {

	public static String toJsonArray(Collection<ResponseInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		String respType = collection.iterator().next().getRespType();

		if (respType.equals("code")) {
			serializer.include("id", "label", "value");
		} else if (respType.equals("numeric")) {
			serializer.include("dataType", "high", "low");
			serializer.transform(new FieldNameTransformer("type"), "dataType");
		} else if (respType.equals("category")) {
			serializer.include("id", "label");
			serializer.transform(new FieldNameTransformer("type"), "dataType");
		}

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return serializer.serialize(collection);
	}

	private String respType;

	private Integer id;

	private String dataType;

	private String label;

	private Integer value;

	private Integer high;

	private Integer low;

	public ResponseInfo(Integer id, String label, Integer value) {
		this.id = id;
		this.label = label;
		this.value = value;
	}

	public ResponseInfo(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public ResponseInfo(String dataType, Integer low, Integer high) {
		this.dataType = dataType;
		this.high = high;
		this.low = low;
	}

	public ResponseInfo(QuestionTypeCode questionTypeCode) {
		this(questionTypeCode.getId().getCodeId(), questionTypeCode.getLabel(), questionTypeCode.getValue());
	}

	public ResponseInfo(QuestionTypeCategory questionTypeCategory) {
		this(questionTypeCategory.getId().getCategoryId(), questionTypeCategory.getLabel());
	}

	public ResponseInfo(QuestionTypeNumeric questionTypeNumeric) {
		this(questionTypeNumeric.getTypeQstn(), questionTypeNumeric.getLow(), questionTypeNumeric.getHigh());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public String getRespType() {
		return respType;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getLow() {
		return low;
	}

	public void setLow(Integer low) {
		this.low = low;
	}

	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		if (respType.equals("code")) {
			serializer.include("id", "label", "value");
		} else if (respType.equals("numeric")) {
			serializer.include("dataType", "high", "low");
			serializer.transform(new FieldNameTransformer("type"), "dataType");
		} else if (respType.equals("category")) {
			serializer.include("id", "label");
			serializer.transform(new FieldNameTransformer("type"), "dataType");
		}

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return serializer.serialize(this);
	}

}
