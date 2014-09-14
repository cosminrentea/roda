package ro.roda.transformer;

import ro.roda.domain.Question;
import flexjson.BasicType;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class FlatQuestionMinInfoTransformer extends AbstractTransformer {

	String prefix = "";

	public FlatQuestionMinInfoTransformer(String prefix) {
		this.prefix = prefix;
	}

	public void transform(Object o) {

		boolean setContext = false;

		TypeContext typeContext = getContext().peekTypeContext();
		String propertyName = typeContext != null ? typeContext.getPropertyName() : "";
		if (prefix.trim().equals(""))
			prefix = propertyName;

		if (typeContext == null || typeContext.getBasicType() != BasicType.OBJECT) {
			typeContext = getContext().writeOpenObject();
			setContext = true;
		}

		Question question = (Question) o;

		if (!typeContext.isFirst())
			getContext().writeComma();
		// typeContext.setFirst(false);
		typeContext.increment();

		getContext().writeName(fieldName("id"));
		getContext().write(question.getId().toString());

		getContext().writeComma();
		getContext().writeName(fieldName("statement"));
		getContext().writeQuoted(question.getStatement());

		if (setContext) {
			getContext().writeCloseObject();
		}

	}

	private String fieldName(String suffix) {
		if (prefix.trim().equals("")) {
			return suffix.toLowerCase();
		} else {
			return prefix + suffix;
		}
	}

	@Override
	public Boolean isInline() {
		return Boolean.TRUE;
	}

}
