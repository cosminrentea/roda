package ro.roda.transformer;

import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class FieldNameTransformer extends AbstractTransformer {

	private String transformedFieldName;

	public FieldNameTransformer(String transformedFieldName) {
		this.transformedFieldName = transformedFieldName;
	}

	public void transform(Object object) {

		TypeContext typeContext = getContext().peekTypeContext();

		// virgula inainte de prima proprietate
		if (!typeContext.isFirst())
			getContext().writeComma();

		// metoda setFirst nu mai exista; de fapt, verifica (count == 0), de
		// aceea il incrementam pe count
		// typeContext.setFirst(false);
		typeContext.increment();

		getContext().writeName(getTransformedFieldName());

		if (object == null) {
			getContext().write(null);
		} else {
			getContext().writeQuoted(object.toString());
		}

	}

	/***
	 * TRUE tells the JSONContext that this class will be handling the writing
	 * of our property name by itself.
	 */
	@Override
	public Boolean isInline() {
		return Boolean.TRUE;
	}

	public String getTransformedFieldName() {
		return this.transformedFieldName;
	}
}
