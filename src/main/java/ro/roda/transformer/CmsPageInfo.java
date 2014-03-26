package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsPage;
import flexjson.JSONSerializer;

@Configurable
public class CmsPageInfo extends JsonInfo implements Comparable<CmsPageInfo> {

	public static String toJsonArray(Collection<CmsPageInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "lang", "menutitle", "synopsis", "target", "url", "defaultPage",
				"externalredirect", "internalredirect", "layout", "layoutid", "cacheable", "published", "pagetype",
				"pagetypeid", "parent", "parentid", "navigable", "searchable", "content");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<CmsPageInfo> findAllCmsPageInfos() {
		List<CmsPageInfo> result = new ArrayList<CmsPageInfo>();

		List<CmsPage> pages = CmsPage.findAllCmsPages();

		if (pages != null && pages.size() > 0) {

			Iterator<CmsPage> pagesIterator = pages.iterator();
			while (pagesIterator.hasNext()) {
				CmsPage page = (CmsPage) pagesIterator.next();
				CmsPageInfo cmsPageInfo = new CmsPageInfo(page);
				boolean leaf = (page.getCmsPages() == null ? true : page.getCmsPages().size() == 0);
				cmsPageInfo.setLeaf(leaf);
				result.add(cmsPageInfo);
			}
		}

		return result;
	}

	public static CmsPageInfo findCmsPageInfo(Integer id) {
		CmsPage page = CmsPage.findCmsPage(id);

		if (page != null) {
			CmsPageInfo cmsPageInfo = new CmsPageInfo(page);
			boolean leaf = (page.getCmsPages() == null ? true : page.getCmsPages().size() == 0);
			cmsPageInfo.setLeaf(leaf);
			return cmsPageInfo;
		}

		return null;
	}

	private Integer id;

	private String name;

	private String lang;

	private String menutitle;

	private String synopsis;

	private String target;

	private String url;

	private boolean defaultPage;

	private String externalredirect;

	private String internalredirect;

	private String layout;

	private Integer layoutid;

	private Integer cacheable;

	private boolean published;

	private String pagetype;

	private Integer pagetypeid;

	private String content;

	private String parent;

	private Integer parentid;

	private boolean navigable;

	private boolean searchable;

	private boolean leaf;

	public CmsPageInfo(Integer id, String name, String lang, String menutitle, String synopsis, String target,
			String url, boolean defaultPage, String externalredirect, String internalredirect, String layout,
			Integer layoutid, Integer cacheable, boolean published, String pagetype, Integer pagetypeid, String parent,
			Integer parentid, boolean navigable, boolean searchable, String content) {
		this.id = id;
		this.name = name;
		this.lang = lang;
		this.menutitle = menutitle;
		this.synopsis = synopsis;
		this.target = target;
		this.url = url;
		this.defaultPage = defaultPage;
		this.externalredirect = externalredirect;
		this.internalredirect = internalredirect;
		this.layout = layout;
		this.layoutid = layoutid;
		this.cacheable = cacheable;
		this.published = published;
		this.pagetype = pagetype;
		this.pagetypeid = pagetypeid;
		this.parent = parent;
		this.parentid = parentid;
		this.navigable = navigable;
		this.searchable = searchable;
		this.content = content;
	}

	public CmsPageInfo(CmsPage cmsPage) {
		this(cmsPage.getId(), cmsPage.getName(), (cmsPage.getCmsPageLangId() != null && cmsPage.getCmsPageLangId()
				.size() > 0) ? cmsPage.getCmsPageLangId().iterator().next().getLangId().getIso639() : null, cmsPage
				.getMenuTitle(), cmsPage.getSynopsis(), cmsPage.getTarget(), cmsPage.getUrl(), cmsPage.isDefaultPage(),
				cmsPage.getExternalRedirect(), cmsPage.getInternalRedirect(),
				(cmsPage.getCmsLayoutId() != null ? cmsPage.getCmsLayoutId().getName() : null), (cmsPage
						.getCmsLayoutId() != null ? cmsPage.getCmsLayoutId().getId() : null), cmsPage.getCacheable(),
				cmsPage.isVisible(), cmsPage.getCmsPageTypeId() == null ? null : cmsPage.getCmsPageTypeId().getName(),
				cmsPage.getCmsPageTypeId() == null ? null : cmsPage.getCmsPageTypeId().getId(),
				cmsPage.getCmsPageId() == null ? null : cmsPage.getCmsPageId().getName(),
				cmsPage.getCmsPageId() == null ? null : cmsPage.getCmsPageId().getId(), cmsPage.isNavigable(), cmsPage
						.isSearchable(),
				(cmsPage.getCmsPageContents() != null && cmsPage.getCmsPageContents().size() > 0) ? cmsPage
						.getCmsPageContents().iterator().next().getContentText() : null);
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

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getMenutitle() {
		return menutitle;
	}

	public void setMenutitle(String menutitle) {
		this.menutitle = menutitle;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(boolean defaultPage) {
		this.defaultPage = defaultPage;
	}

	public String getExternalredirect() {
		return externalredirect;
	}

	public void setExternalredirect(String externalredirect) {
		this.externalredirect = externalredirect;
	}

	public String getInternalredirect() {
		return internalredirect;
	}

	public void setInternalredirect(String internalredirect) {
		this.internalredirect = internalredirect;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public Integer getCacheable() {
		return cacheable;
	}

	public void setCacheable(Integer cacheable) {
		this.cacheable = cacheable;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getPagetype() {
		return pagetype;
	}

	public void setPagetype(String pagetype) {
		this.pagetype = pagetype;
	}

	public Integer getPagetypeid() {
		return pagetypeid;
	}

	public void setPagetypeid(Integer pagetypeid) {
		this.pagetypeid = pagetypeid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLayoutid() {
		return layoutid;
	}

	public void setLayoutid(Integer layoutid) {
		this.layoutid = layoutid;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentId) {
		this.parentid = parentId;
	}

	public boolean isNavigable() {
		return navigable;
	}

	public void setNavigable(boolean navigable) {
		this.navigable = navigable;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "lang", "menutitle", "synopsis", "target", "url", "defaultPage",
				"externalredirect", "internalredirect", "layout", "layoutid", "cacheable", "published", "pagetype",
				"pagetypeid", "parent", "parentid", "navigable", "searchable", "content");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(new FieldNameTransformer("title"), "name");
		serializer.transform(new FieldNameTransformer("default"), "defaultPage");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(CmsPageInfo cmsPageInfo) {
		// TODO
		return id < cmsPageInfo.getId() ? -1 : (id > cmsPageInfo.getId() ? 1 : 0);
	}

	@Override
	public boolean equals(Object other) {
		// TODO
		if (other != null && other instanceof CmsPageInfo) {
			return new EqualsBuilder().append(this.getId(), ((CmsPageInfo) other).getId()).isEquals();
		} else {
			return false;
		}
	}

}
