package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsPageType;
import flexjson.JSONSerializer;

@Configurable
public class CmsPageTypeInfo extends JsonInfo implements Comparable<CmsPageTypeInfo> {

	public static String toJsonArray(Collection<CmsPageTypeInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "type");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("description"), "type");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<CmsPageTypeInfo> findAllCmsPageTypeInfos() {
		List<CmsPageTypeInfo> result = new ArrayList<CmsPageTypeInfo>();

		List<CmsPageType> pageTypes = CmsPageType.findAllCmsPageTypes();

		if (pageTypes != null && pageTypes.size() > 0) {

			Iterator<CmsPageType> pageTypesIterator = pageTypes.iterator();
			while (pageTypesIterator.hasNext()) {
				CmsPageType pageType = (CmsPageType) pageTypesIterator.next();
				result.add(new CmsPageTypeInfo(pageType));
			}
		}

		return result;
	}

	public static CmsPageTypeInfo findCmsPageTypeInfo(Integer id) {
		CmsPageType pageType = CmsPageType.findCmsPageType(id);

		if (pageType != null) {
			return new CmsPageTypeInfo(pageType);
		}

		return null;
	}

	public CmsPageTypeInfo(Integer id, String name, String description) {
		super(id, name, description);
	}

	public CmsPageTypeInfo(CmsPageType cmsPageType) {
		this(cmsPageType.getId(), cmsPageType.getName(), cmsPageType.getDescription());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "type");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("description"), "type");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(CmsPageTypeInfo cmsPageTypeInfo) {
		// TODO
		return getId() < cmsPageTypeInfo.getId() ? -1 : (getId() > cmsPageTypeInfo.getId() ? 1 : 0);
	}

	@Override
	public boolean equals(Object other) {
		// TODO
		if (other != null && other instanceof CmsPageTypeInfo) {
			return new EqualsBuilder().append(this.getId(), ((CmsPageTypeInfo) other).getId()).isEquals();
		} else {
			return false;
		}
	}

}
