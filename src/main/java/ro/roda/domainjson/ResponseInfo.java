package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.QuestionTypeCategory;
import ro.roda.domain.QuestionTypeNumeric;
import ro.roda.domain.QuestionTypeString;
import flexjson.JSONSerializer;

@Configurable
public class ResponseInfo extends JsonInfo {

	public static String toJsonArray(Collection<ResponseInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		return serializer.serialize(collection);
	}

	private String respType;

	private Integer id;

	private String dataType;

	private String label;

	private Long numberValue;

	private String stringValue;

	private Long high;

	private Long low;

	private Boolean interpretation;

	private Boolean numericValues;

	// TODO: add one more field in question_type_numeric
	private static long VAL = 1;

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
		this(questionTypeString.getId().getValue(), questionTypeString.getLabel(), VAL++);
	}

	public ResponseInfo(QuestionTypeCategory questionTypeCategory) {
		this(questionTypeCategory.getId().getCategoryId(), questionTypeCategory.getLabel());
	}

	public ResponseInfo(QuestionTypeNumeric questionTypeNumeric) {
		this(questionTypeNumeric.getDataType(), questionTypeNumeric.getLow(), questionTypeNumeric.getHigh());
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

		return serializer.serialize(this);
	}

}
