package ro.roda.transformer;

import ro.roda.domain.CmsPageType;
import flexjson.BasicType;
import flexjson.TypeContext;
import flexjson.transformer.AbstractTransformer;

public class FlatCmsPageTypeTransformer extends AbstractTransformer {

	String prefix = "";

	public FlatCmsPageTypeTransformer(String prefix) {
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

		CmsPageType cmsPageType = (CmsPageType) o;

		if (!typeContext.isFirst())
			getContext().writeComma();
		// typeContext.setFirst(false);
		typeContext.increment();

		getContext().writeName(fieldName("id"));
		getContext().write(cmsPageType.getId().toString());

		getContext().writeComma();
		getContext().writeName(fieldName("name"));
		getContext().writeQuoted(cmsPageType.getName());

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
