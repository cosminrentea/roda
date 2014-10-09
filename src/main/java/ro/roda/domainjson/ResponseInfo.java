package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeNumeric;
import ro.roda.domain.QuestionTypeString;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class ResponseInfo extends JsonInfo {

	public static String toJsonArray(Collection<ResponseInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		String respType = collection.iterator().next().getRespType();

		if (respType.equals("String")) {
			serializer.include("freeText", "label");

			serializer.exclude("respType", "dataType", "numberValue", "stringValue", "high", "low", "interpretation",
					"numericValues");

		} else if (respType.equals("Numeric")) {
			serializer.include("dataType", "high", "low", "numberValue", "interpretation");

			serializer.exclude("respType", "label", "freeText", "stringValue");

			serializer.transform(new FieldNameTransformer("type"), "dataType");
			serializer.transform(new FieldNameTransformer("value"), "numberValue");
			serializer.transform(new FieldNameTransformer("category_interpretation"), "interpretation");
		} else if (respType.equals("Category")) {
			serializer.include("label", "stringValue", "numericValues", "interpretation");

			serializer.exclude("respType", "dataType", "freeText", "numberValue", "high", "low");

			serializer.transform(new FieldNameTransformer("value"), "stringValue");
			serializer.transform(new FieldNameTransformer("is_numeric"), "numericValues");
			serializer.transform(new FieldNameTransformer("numeric_interpretation"), "interpretation");
		}

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return serializer.serialize(collection);
	}

	private String respType;

	private Integer id;

	private String dataType;

	private String label;

	private String freeText;

	private Long numberValue;

	private String stringValue;

	private Long high;

	private Long low;

	private Boolean interpretation;

	private Boolean numericValues;

	public ResponseInfo(Integer id, String label, Long value) {
		this.id = id;
		this.label = label;
		this.numberValue = value;
	}

	public ResponseInfo(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public ResponseInfo(String dataType, Long low, Long high) {
		this.dataType = dataType;
		this.high = high;
		this.low = low;
	}

	public ResponseInfo(QuestionTypeString questionTypeString) {
		this(questionTypeString.getId().getStringId(), "");
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

	public String getRespType() {
		return respType;
	}

	public void setRespType(String respType) {
		this.respType = respType;
	}

	public Long getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(Long value) {
		this.numberValue = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Long getLow() {
		return low;
	}

	public void setLow(Long low) {
		this.low = low;
	}

	public Long getHigh() {
		return high;
	}

	public void setHigh(Long high) {
		this.high = high;
	}

	public Boolean getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(Boolean interpretation) {
		this.interpretation = interpretation;
	}

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Boolean getNumericValues() {
		return numericValues;
	}

	public void setNumericValues(Boolean numericValues) {
		this.numericValues = numericValues;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		if (respType.equals("String")) {
			serializer.include("freeText", "label");

			serializer.exclude("respType", "dataType", "numberValue", "stringValue", "high", "low", "interpretation",
					"numericValues");

		} else if (respType.equals("Numeric")) {
			serializer.include("dataType", "high", "low", "numberValue", "interpretation");

			serializer.exclude("respType", "label", "freeText", "stringValue");

			serializer.transform(new FieldNameTransformer("type"), "dataType");
			serializer.transform(new FieldNameTransformer("value"), "numberValue");
			serializer.transform(new FieldNameTransformer("category_interpretation"), "interpretation");
		} else if (respType.equals("Category")) {
			serializer.include("label", "stringValue", "numericValues", "interpretation");

			serializer.exclude("respType", "dataType", "freeText", "numberValue", "high", "low");

			serializer.transform(new FieldNameTransformer("value"), "stringValue");
			serializer.transform(new FieldNameTransformer("is_numeric"), "numericValues");
			serializer.transform(new FieldNameTransformer("numeric_interpretation"), "interpretation");
		}

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return serializer.serialize(this);
	}

}
