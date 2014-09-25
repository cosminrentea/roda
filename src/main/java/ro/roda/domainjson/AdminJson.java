package ro.roda.domainjson;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.Authorities;
import ro.roda.domain.AuthoritiesPK;
import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageContent;
import ro.roda.domain.CmsPageType;
import ro.roda.domain.CmsSnippet;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.domain.Lang;
import ro.roda.domain.News;
import ro.roda.domain.UserGroup;
import ro.roda.domain.UserMessage;
import ro.roda.domain.Users;
import flexjson.JSONSerializer;

@Configurable
public class AdminJson {

	private static String PAGE_MOVE_APPEND_MODE = "append";
	private static String PAGE_MOVE_AFTER_MODE = "after";
	private static String PAGE_MOVE_BEFORE_MODE = "before";

	public static String toJsonArray(Collection<AdminJson> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");

		return serializer.serialize(collection);
	}

	public static AdminJson findLogin(String username, String password) {
		if (username == null)
			return null;

		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em
				.createQuery(
						"SELECT o FROM Users AS o WHERE LOWER(o.username) LIKE LOWER(:username)  AND LOWER(o.password) LIKE LOWER(:password)  AND o.enabled = true",
						Users.class);
		q.setParameter("username", username);
		q.setParameter("password", password);

		List<Users> queryResult = q.getResultList();
		if (queryResult.size() > 0) {
			return new AdminJson(true, "Auth ok");
		} else {
			return new AdminJson(false, "Auth failed");
		}
	}

	public static AdminJson layoutGroupSave(String groupname, Integer parentId, String description) {
		if (groupname == null) {
			return new AdminJson(false, "The layout group must have a name.");
		}

		CmsLayoutGroup layoutGroup = new CmsLayoutGroup();
		layoutGroup.setName(groupname);
		layoutGroup.setDescription(description);

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(parentId);

		try {
			if (parentGroup != null) {

				if (parentGroup.getCmsLayoutGroups() != null && parentGroup.getCmsLayoutGroups().contains(layoutGroup)) {
					// do nothing
				} else {

					if (parentGroup.getCmsLayoutGroups() == null) {
						parentGroup.setCmsLayoutGroups(new HashSet<CmsLayoutGroup>());
						parentGroup.getCmsLayoutGroups().add(layoutGroup);
					} else {
						parentGroup.getCmsLayoutGroups().add(layoutGroup);
					}

					CmsPage.entityManager().persist(parentGroup);

				}
				layoutGroup.setParentId(parentGroup);

			}
			CmsLayoutGroup.entityManager().persist(layoutGroup);

		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not created " + e.getMessage());
		}

		return new AdminJson(true, "Layout group created successfully", layoutGroup.getId());
	}

	public static AdminJson layoutGroupEmpty(Integer groupId) {
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		if (layoutGroup == null)
			return new AdminJson(false, "The layout group does not exist.");

		Set<CmsLayoutGroup> childGroups = layoutGroup.getCmsLayoutGroups();
		Iterator<CmsLayoutGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsLayoutGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsLayoutGroup.entityManager().persist(childGroup);
		}

		Set<CmsLayout> childLayouts = layoutGroup.getCmsLayouts();
		Iterator<CmsLayout> childLayoutsIterator = childLayouts.iterator();
		while (childLayoutsIterator.hasNext()) {
			CmsLayout childLayout = childLayoutsIterator.next();
			childLayout.setCmsLayoutGroupId(null);
			CmsLayout.entityManager().persist(childLayout);
		}

		layoutGroup.setCmsLayoutGroups(new HashSet<CmsLayoutGroup>());
		layoutGroup.setCmsLayouts(new HashSet<CmsLayout>());

		try {
			CmsLayoutGroup.entityManager().persist(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group emptied successfully");
	}

	public static AdminJson layoutGroupDrop(Integer groupId) {
		CmsLayoutGroup layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		if (layoutGroup == null)
			return new AdminJson(false, "The layout group does not exist.");

		Set<CmsLayoutGroup> childGroups = layoutGroup.getCmsLayoutGroups();
		Iterator<CmsLayoutGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsLayoutGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsLayoutGroup.entityManager().persist(childGroup);
		}

		Set<CmsLayout> childLayouts = layoutGroup.getCmsLayouts();
		Iterator<CmsLayout> childLayoutsIterator = childLayouts.iterator();
		while (childLayoutsIterator.hasNext()) {
			CmsLayout childLayout = childLayoutsIterator.next();
			childLayout.setCmsLayoutGroupId(null);
			CmsLayout.entityManager().persist(childLayout);
		}

		try {
			CmsLayoutGroup.entityManager().remove(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout group not dropped: " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group dropped successfully");
	}

	public static AdminJson layoutDrop(Integer layoutId) {
		CmsLayout layout = CmsLayout.findCmsLayout(layoutId);
		if (layout == null)
			return new AdminJson(false, "The layout does not exist");
		if (layout.getCmsPages() != null && layout.getCmsPages().size() > 0) {
			return new AdminJson(false, "The layout could not be dropped because some pages are using it");
		}

		try {
			CmsLayoutGroup parentGroup = layout.getCmsLayoutGroupId();
			if (parentGroup != null && parentGroup.getCmsLayouts() != null
					&& parentGroup.getCmsLayouts().contains(layout)) {
				parentGroup.getCmsLayouts().remove(layout);
			}

			CmsLayout.entityManager().remove(layout);
		} catch (Exception e) {
			return new AdminJson(false, "CMS Layout not dropped: " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout dropped successfully");
	}

	public static AdminJson layoutSave(Integer groupId, String content, String name, String description,
			Integer layoutId) {

		CmsLayout layout = null;
		if (layoutId != null) {
			layout = CmsLayout.findCmsLayout(layoutId);
		}

		if (layout == null) {
			layout = new CmsLayout();
		}

		layout.setName(name);
		layout.setLayoutContent(content);
		layout.setDescription(description);

		try {
			CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
			if (parentGroup != null) {
				// layout.setCmsLayoutGroupId(parentGroup);
				if (parentGroup.getCmsLayouts() != null && parentGroup.getCmsLayouts().contains(layout)) {
					// do nothing
				} else {
					if (parentGroup.getCmsLayouts() == null) {
						parentGroup.setCmsLayouts(new HashSet<CmsLayout>());
						parentGroup.getCmsLayouts().add(layout);
					} else {
						parentGroup.getCmsLayouts().add(layout);
					}
					CmsLayoutGroup.entityManager().persist(parentGroup);
				}
				layout.setCmsLayoutGroupId(parentGroup);
			}

			CmsLayout.entityManager().persist(layout);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout not created " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout created successfully");
	}

	public static AdminJson layoutMove(Integer groupId, Integer layoutId) {

		CmsLayout layout = null;
		if (layoutId != null) {
			layout = CmsLayout.findCmsLayout(layoutId);
		}

		if (layout == null) {
			return new AdminJson(false, "CMS Layout to be moved should exist");
		}

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);

		if (groupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Layout group should exist");
		}

		if (groupId == (layout.getCmsLayoutGroupId() == null ? null : layout.getCmsLayoutGroupId().getId())) {
			return new AdminJson(false, "The  group of the CMS Layout doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsLayouts() == null) {
					parentGroup.setCmsLayouts(new HashSet<CmsLayout>());
					parentGroup.getCmsLayouts().add(layout);
				} else {
					parentGroup.getCmsLayouts().add(layout);
				}
				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
			layout.setCmsLayoutGroupId(parentGroup);

			CmsLayout.entityManager().persist(layout);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Layout not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout moved successfully");
	}

	public static AdminJson layoutGroupMove(Integer parentGroupId, Integer groupId) {

		CmsLayoutGroup layoutGroup = null;
		if (groupId != null) {
			layoutGroup = CmsLayoutGroup.findCmsLayoutGroup(groupId);
		}

		if (layoutGroup == null) {
			return new AdminJson(false, "CMS Layout Group to be moved should exist");
		}

		CmsLayoutGroup parentGroup = CmsLayoutGroup.findCmsLayoutGroup(parentGroupId);

		if (parentGroupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Layout group should exist");
		}

		if (parentGroupId == (layoutGroup.getParentId() == null ? null : layoutGroup.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Layout group doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsLayoutGroups() == null) {
					parentGroup.setCmsLayoutGroups(new HashSet<CmsLayoutGroup>());
				}
				parentGroup.getCmsLayoutGroups().add(layoutGroup);

				CmsLayoutGroup.entityManager().persist(parentGroup);
			}
			layoutGroup.setParentId(parentGroup);

			CmsLayoutGroup.entityManager().persist(layoutGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Layout group not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Layout group moved successfully");
	}

	// Methods for Snippets
	public static AdminJson snippetGroupSave(String groupname, Integer parentId, String description) {
		if (groupname == null) {
			return new AdminJson(false, "The snippet group must gave a name.");
		}

		CmsSnippetGroup snippetGroup = new CmsSnippetGroup();
		snippetGroup.setName(groupname);
		snippetGroup.setDescription(description);

		try {
			CmsSnippetGroup parentGroup = null;
			if (parentId != null) {
				parentGroup = CmsSnippetGroup.findCmsSnippetGroup(parentId);
			}
			snippetGroup.setParentId(parentGroup);

			if (parentGroup != null) {
				Set<CmsSnippetGroup> subGroups = parentGroup.getCmsSnippetGroups();
				if (subGroups == null) {
					subGroups = new HashSet<CmsSnippetGroup>();
				}
				subGroups.add(snippetGroup);
				parentGroup.setCmsSnippetGroups(subGroups);
			}

			snippetGroup.persist();
			if (parentGroup != null) {
				parentGroup.merge();
			}
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not created " + e.getMessage());
		}

		return new AdminJson(true, "Snippet group created successfully", snippetGroup.getId());
	}

	public static AdminJson snippetGroupEmpty(Integer groupId) {
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (snippetGroup == null)
			return new AdminJson(false, "The snippet group does not exist.");

		Set<CmsSnippetGroup> childGroups = snippetGroup.getCmsSnippetGroups();
		Iterator<CmsSnippetGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsSnippetGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsSnippetGroup.entityManager().persist(childGroup);
		}

		Set<CmsSnippet> childSnippets = snippetGroup.getCmsSnippets();
		Iterator<CmsSnippet> childSnippetsIterator = childSnippets.iterator();
		while (childSnippetsIterator.hasNext()) {
			CmsSnippet childSnippet = childSnippetsIterator.next();
			childSnippet.setCmsSnippetGroupId(null);
			CmsSnippet.entityManager().persist(childSnippet);
		}

		snippetGroup.setCmsSnippetGroups(new HashSet<CmsSnippetGroup>());
		snippetGroup.setCmsSnippets(new HashSet<CmsSnippet>());

		try {
			CmsSnippetGroup.entityManager().persist(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group emptied successfully");
	}

	public static AdminJson snippetGroupDrop(Integer groupId) {
		CmsSnippetGroup snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (snippetGroup == null)
			return new AdminJson(false, "The snippet group does not exist.");

		Set<CmsSnippetGroup> childGroups = snippetGroup.getCmsSnippetGroups();
		Iterator<CmsSnippetGroup> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsSnippetGroup childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsSnippetGroup.entityManager().persist(childGroup);
		}

		Set<CmsSnippet> childSnippets = snippetGroup.getCmsSnippets();
		Iterator<CmsSnippet> childSnippetsIterator = childSnippets.iterator();
		while (childSnippetsIterator.hasNext()) {
			CmsSnippet childSnippet = childSnippetsIterator.next();
			childSnippet.setCmsSnippetGroupId(null);
			CmsSnippet.entityManager().persist(childSnippet);
		}

		try {
			CmsSnippetGroup.entityManager().remove(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet group not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group dropped successfully");
	}

	public static AdminJson snippetDrop(Integer snippetId) {
		CmsSnippet snippet = CmsSnippet.findCmsSnippet(snippetId);
		if (snippet == null)
			return new AdminJson(false, "The snippet does not exist.");

		Set<JsonInfo> snippetUsage = SnippetInfo.findSnippetInfo(snippet.getId()).getSnippetUsage();
		if (snippetUsage != null && snippetUsage.size() > 0) {
			return new AdminJson(false,
					"The snippet could not be dropped because it is used in pages, layouts or other snippets.");
		}

		try {
			CmsSnippetGroup parentGroup = snippet.getCmsSnippetGroupId();
			if (parentGroup != null && parentGroup.getCmsSnippets() != null
					&& parentGroup.getCmsSnippets().contains(snippet)) {
				parentGroup.getCmsSnippets().remove(snippet);
			}

			CmsSnippet.entityManager().remove(snippet);
		} catch (Exception e) {
			return new AdminJson(false, "CMS Snippet not dropped" + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet dropped successfully");
	}

	public static AdminJson snippetSave(Integer groupId, String name, String content, Integer snippetId) {

		CmsSnippet snippet = null;
		if (snippetId != null) {
			snippet = CmsSnippet.findCmsSnippet(snippetId);
		}

		if (snippet == null) {
			snippet = new CmsSnippet();
		}

		snippet.setName(name);
		snippet.setSnippetContent(content);

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		if (parentGroup != null) {
			snippet.setCmsSnippetGroupId(parentGroup);
			if (parentGroup.getCmsSnippets() != null && parentGroup.getCmsSnippets().contains(snippet)) {
				// do nothing
			} else {
				if (parentGroup.getCmsSnippets() == null) {
					parentGroup.setCmsSnippets(new HashSet<CmsSnippet>());
				}
				parentGroup.getCmsSnippets().add(snippet);

				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
		}

		try {
			CmsSnippet.entityManager().persist(snippet);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Snippet not created or modified" + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet created or modified successfully");
	}

	public static AdminJson snippetMove(Integer groupId, Integer snippetId) {

		CmsSnippet snippet = null;
		if (snippetId != null) {
			snippet = CmsSnippet.findCmsSnippet(snippetId);
		}

		if (snippet == null) {
			return new AdminJson(false, "CMS Snippet to be moved should exist");
		}

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);

		if (groupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Snippet group should exist");
		}

		if (groupId == (snippet.getCmsSnippetGroupId() == null ? null : snippet.getCmsSnippetGroupId().getId())) {
			return new AdminJson(false, "The  group of the CMS Snippet doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsSnippets() == null) {
					parentGroup.setCmsSnippets(new HashSet<CmsSnippet>());
					parentGroup.getCmsSnippets().add(snippet);
				} else {
					parentGroup.getCmsSnippets().add(snippet);
				}
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
			snippet.setCmsSnippetGroupId(parentGroup);

			CmsSnippet.entityManager().persist(snippet);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "Snippet not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet moved successfully");
	}

	public static AdminJson snippetGroupMove(Integer parentGroupId, Integer groupId) {

		CmsSnippetGroup snippetGroup = null;
		if (groupId != null) {
			snippetGroup = CmsSnippetGroup.findCmsSnippetGroup(groupId);
		}

		if (snippetGroup == null) {
			return new AdminJson(false, "CMS Snippet Group to be moved should exist");
		}

		CmsSnippetGroup parentGroup = CmsSnippetGroup.findCmsSnippetGroup(parentGroupId);

		if (parentGroupId != null && parentGroup == null) {
			return new AdminJson(false, "The CMS Snippet group should exist");
		}

		if (parentGroupId == (snippetGroup.getParentId() == null ? null : snippetGroup.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Snippet group doesn't change");
		}

		try {
			if (parentGroup != null) {
				if (parentGroup.getCmsSnippetGroups() == null) {
					parentGroup.setCmsSnippetGroups(new HashSet<CmsSnippetGroup>());
					parentGroup.getCmsSnippetGroups().add(snippetGroup);
				} else {
					parentGroup.getCmsSnippetGroups().add(snippetGroup);
				}
				CmsSnippetGroup.entityManager().persist(parentGroup);
			}
			snippetGroup.setParentId(parentGroup);

			CmsSnippetGroup.entityManager().persist(snippetGroup);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Snippet group not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Snippet group moved successfully");
	}

	// Methods for News
	public static AdminJson newsSave(Integer id, Integer langId, String title, String content, Date added) {
		if (title == null) {
			return new AdminJson(false, "Title must not be empty.");
		}
		if (langId == null) {
			return new AdminJson(false, "Language is required.");
		}
		if (content == null) {
			return new AdminJson(false, "No news without content");
		}

		News newsitem = null;
		if (id != null) {
			newsitem = News.findNews(id);
		}

		if (newsitem == null) {
			newsitem = new News();
		}

		newsitem.setTitle(title);
		newsitem.setContent(content);
		newsitem.setAdded(added);
		newsitem.setVisible(true);

		if (langId != null) {
			Lang newsLang = Lang.findLang(langId);
			if (newsLang != null) {
				newsitem.setLangId(newsLang);

			}
		}

		newsitem.persist();

		return new AdminJson(true, "News item saved");
	}

	// Cms Page Management Methods
	public static AdminJson cmsPageSave(Boolean save, Integer cmsPageParentId, String name, String langName,
			String menutitle, String synopsis, String target, String url, Boolean defaultPage, String externalredirect,
			String internalredirect, Integer layoutId, Integer cacheable, Boolean published, Boolean navigable,
			String pagetype, Integer cmsPageId, String pageContent, AtomicReference<CmsPage> cmsPage) {

		CmsPage page = null;

		if (cmsPageId != null) {
			page = CmsPage.findCmsPage(cmsPageId);
		}

		if (page == null) {
			page = new CmsPage();
		}

		if (name != null) {
			page.setName(name);
		}
		if (menutitle != null) {
			page.setMenuTitle(menutitle);
		}
		if (synopsis != null) {
			page.setSynopsis(synopsis);
		}
		if (target != null) {
			page.setTarget(target);
		}
		if (defaultPage != null) {
			page.setDefaultPage(defaultPage);
		}
		if (externalredirect != null) {
			page.setExternalRedirect(externalredirect);
		}
		if (internalredirect != null) {
			page.setInternalRedirect(internalredirect);
		}
		if (cacheable != null) {
			page.setCacheable(cacheable);
		}
		// TODO check if visible = published
		if (published != null) {
			page.setVisible(published);
		}

		if (navigable != null) {
			page.setNavigable(navigable);
		}

		// the following properties require updates in both endings of the
		// one-to-many relations and are prone to runtime errors if the save in
		// the database is not done

		if (save) {
			if (layoutId != null) {
				CmsLayout layout = CmsLayout.findCmsLayout(layoutId);

				if (layout.getCmsPages() == null) {
					layout.setCmsPages(new HashSet<CmsPage>());
					layout.getCmsPages().add(page);
				} else {
					layout.getCmsPages().add(page);
				}

				CmsPage.entityManager().persist(layout);

				page.setCmsLayoutId(layout);
			}

			if (pagetype != null) {
				page.setCmsPageTypeId(CmsPageType.checkCmsPageType(null, pagetype, null));
			}

			try {
				if (cmsPageParentId != null) {
					CmsPage parentPage = CmsPage.findCmsPage(cmsPageParentId);
					if (parentPage != null) {
						// layout.setCmsLayoutGroupId(parentGroup);
						if (parentPage.getCmsPages() != null && parentPage.getCmsPages().contains(page)) {
							// do nothing
						} else {

							if (parentPage.getCmsPages() == null) {
								parentPage.setCmsPages(new HashSet<CmsPage>());
								parentPage.getCmsPages().add(page);
							} else {
								parentPage.getCmsPages().add(page);
							}

							CmsPage.entityManager().persist(parentPage);

						}
						page.setCmsPageId(parentPage);
					}
				}

				if (url != null) {
					page.setUrl(processPageUrl(url, page.getName(), page, page.getCmsPageId()));
				}

				// set the page language
				if (langName != null) {
					Lang lang = Lang.findLang(langName);
					page.setLangId(lang);
					Set<CmsPage> pages = lang.getCmsPages();
					pages.add(page);
					lang.setCmsPages(pages);
					lang.merge();
				}

				CmsPage.entityManager().persist(page);

				// set the page content
				if (pageContent != null) {
					Set<CmsPageContent> oldContent = page.getCmsPageContents();

					if (oldContent != null) {
						for (CmsPageContent cpc : oldContent) {
							cpc.remove();
						}
					}
					CmsPageContent cmsPageContent = new CmsPageContent();
					cmsPageContent.setContentText(pageContent);

					cmsPageContent.setCmsPageId(page);

					cmsPageContent.persist();

					Set<CmsPageContent> pageContents = new HashSet<CmsPageContent>();
					pageContents.add(cmsPageContent);
					page.setCmsPageContents(pageContents);
				}

			} catch (EntityExistsException e) {
				return new AdminJson(false, (save ? "Cms Page not created or modified"
						: "Cms Page preview not generated") + e.getMessage());
			}
		} else {
			cmsPage.set(page);
		}
		return new AdminJson(true, save ? "CMS Page created or modified successfully" : "");
	}

	public static AdminJson cmsPageMove(Integer cmsPageGroupId, Integer cmsPageId, String mode) {

		if (mode != null && !mode.equals(PAGE_MOVE_AFTER_MODE) && !mode.equals(PAGE_MOVE_BEFORE_MODE)
				&& !mode.equals(PAGE_MOVE_APPEND_MODE)) {
			return new AdminJson(false, "CMS Page not moved because the specified mode isn't valid");
		}

		CmsPage cmsPage = null;
		if (cmsPageId != null) {
			cmsPage = CmsPage.findCmsPage(cmsPageId);
		}

		if (cmsPage == null) {
			return new AdminJson(false, "CMS Page to be moved should exist");
		}

		CmsPage groupPage = CmsPage.findCmsPage(cmsPageGroupId);

		if (cmsPageGroupId != null && groupPage == null) {
			return new AdminJson(false, "The specified CMS Page parent or sibling should exist");
		}

		int position = -1;
		if (!mode.equals(PAGE_MOVE_APPEND_MODE) && groupPage != null) {
			if (groupPage.getSequenceNumber() != null) {
				position = groupPage.getSequenceNumber();
			}
			// in the mode "before" or "after", the page is moved at the same
			// level as the page (group) specified in the first parameter
			groupPage = groupPage.getCmsPageId();
		}

		if (mode.equals(PAGE_MOVE_APPEND_MODE)
				&& cmsPageGroupId == (cmsPage.getCmsPageId() == null ? null : cmsPage.getCmsPageId().getId())
		// || !mode.equals(PAGE_MOVE_APPEND_MODE) && groupPage != null
		// && groupPage.getId() == (cmsPage.getCmsPageId() == null ? null :
		// cmsPage.getCmsPageId().getId())
		) {
			return new AdminJson(false, "The  parent or sibling of the CMS Page doesn't change");
		}

		try {
			if (groupPage != null) {
				if (groupPage.getCmsPages() == null) {
					groupPage.setCmsPages(new HashSet<CmsPage>());
				}
				groupPage.getCmsPages().add(cmsPage);
				CmsPage.entityManager().persist(groupPage);
			}

			cmsPage.setCmsPageId(groupPage);

			if (mode.equals(PAGE_MOVE_BEFORE_MODE)) {
				if (position > 0) {
					cmsPage.move(position - 1);
				} else {
					cmsPage.moveFirst();
				}
			} else if (mode.equals(PAGE_MOVE_AFTER_MODE)) {
				if (position > 0) {
					cmsPage.move(position + 1);
				} else {
					cmsPage.moveLast();
				}
			}

			CmsPage.entityManager().persist(cmsPage);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Page not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Page moved successfully");
	}

	public static AdminJson cmsPageDrop(Integer cmsPageId) {
		CmsPage cmsPage = CmsPage.findCmsPage(cmsPageId);
		if (cmsPage == null)
			return new AdminJson(false, "The cms page does not exist.");

		if (cmsPage.getCmsPages() == null || cmsPage.getCmsPages().size() == 0) {

			try {
				CmsPage parentPage = cmsPage.getCmsPageId();
				if (parentPage.getCmsPages() != null) {
					parentPage.getCmsPages().remove(cmsPage);
				}

				if (cmsPage.getCmsPageContents() != null && cmsPage.getCmsPageContents().size() > 0) {
					for (CmsPageContent content : cmsPage.getCmsPageContents()) {
						CmsPageContent.entityManager().remove(content);
					}
				}

				CmsPage.entityManager().remove(cmsPage);
			} catch (Exception e) {
				return new AdminJson(false, "CMS Page not dropped" + e.getMessage());
			}

			return new AdminJson(true, "CMS Page dropped successfully");
		} else {
			return new AdminJson(false, "CMS Page not dropped because it has child pages");
		}
	}

	private static String processPageUrl(String url, String pageTitle, CmsPage page, CmsPage parent) {
		String result = null;

		if (url != null && !url.trim().equals("")) {
			result = url;
		} else {

			if (pageTitle != null) {
				result = pageTitle.toLowerCase().trim();
				// replace (multiple) spaces with a (single) "-"
				result = result.replaceAll("\\s+", "-");
				result = result.replaceAll("-+", "-");
			}
		}

		// generate a new name, relative to the parent page

		// check if there is already a child of the current parent having the
		// same url
		CmsPage resultPage = CmsPage.findCmsPageByParent(result, parent);

		if (resultPage != null && resultPage != page) {
			int i = 1;
			while (CmsPage.findCmsPageByParent(result + "_" + i, parent) != null) {
				i++;
			}
			result = result + "_" + i;
		}

		return result;
	}

	// Methods for CMS files and folders.
	public static AdminJson folderSave(String foldername, Integer parentId, String description) {
		if (foldername == null) {
			return new AdminJson(false, "CMS folder must have a name.");
		}
		CmsFolder parentFolder = CmsFolder.findCmsFolder(parentId);
		CmsFolder folder = CmsFolder.checkCmsFolder(null, foldername, parentFolder, description);

		if (parentFolder != null) {
			folder.setParentId(parentFolder);
			Set<CmsFolder> children = parentFolder.getCmsFolders();
			if (children == null) {
				children = new HashSet<CmsFolder>();
			}
			children.add(folder);
			parentFolder.setCmsFolders(children);
		}

		try {
			CmsFolder.entityManager().persist(folder);
			if (parentFolder != null) {
				CmsFolder.entityManager().merge(parentFolder);
			}
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Folder not created. Exception: " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder created", folder.getId());
	}

	public static AdminJson folderEmpty(Integer folderId) {
		CmsFolder folder = CmsFolder.findCmsFolder(folderId);
		if (folder == null) {
			return new AdminJson(false, "CMS folder does not exist.");
		}
		Set<CmsFolder> childGroups = folder.getCmsFolders();
		Iterator<CmsFolder> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsFolder childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsFolder.entityManager().persist(childGroup);
		}

		Set<CmsFile> childFiles = folder.getCmsFiles();
		Iterator<CmsFile> childFilesIterator = childFiles.iterator();
		while (childFilesIterator.hasNext()) {
			CmsFile childFile = childFilesIterator.next();
			childFile.setCmsFolderId(null);
			CmsFile.entityManager().persist(childFile);
		}

		folder.setCmsFolders(new HashSet<CmsFolder>());
		folder.setCmsFiles(new HashSet<CmsFile>());

		try {
			CmsFolder.entityManager().persist(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Folder not emptied " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder emptied successfully");
	}

	public static AdminJson folderDrop(Integer folderId) {
		CmsFolder folder = CmsFolder.findCmsFolder(folderId);
		if (folder == null)
			return new AdminJson(false, "CMS Folder does not exist");

		Set<CmsFolder> childGroups = folder.getCmsFolders();
		Iterator<CmsFolder> childGroupsIterator = childGroups.iterator();
		while (childGroupsIterator.hasNext()) {
			CmsFolder childGroup = childGroupsIterator.next();
			childGroup.setParentId(null);
			CmsFolder.entityManager().persist(childGroup);
		}

		Set<CmsFile> childFiles = folder.getCmsFiles();
		Iterator<CmsFile> childFilesIterator = childFiles.iterator();
		while (childFilesIterator.hasNext()) {
			CmsFile childFile = childFilesIterator.next();
			childFile.setCmsFolderId(null);
			CmsFile.entityManager().persist(childFile);
		}

		try {
			CmsFolder.entityManager().remove(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Folder not dropped " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder dropped successfully");
	}

	public static AdminJson fileDrop(Integer fileId) {
		CmsFile file = CmsFile.findCmsFile(fileId);
		if (file == null) {
			return new AdminJson(false, "CMS File does not exist");
		}
		CmsFolder parentGroup = file.getCmsFolderId();
		if (parentGroup.getCmsFiles() != null && parentGroup.getCmsFiles().contains(file)) {
			parentGroup.getCmsFiles().remove(file);
			CmsFolder.entityManager().persist(parentGroup);
		}

		try {
			CmsFile.entityManager().remove(file);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS File not dropped" + e.getMessage());
		}

		return new AdminJson(true, "CMS File dropped successfully");
	}

	public static AdminJson fileSave(Integer folderId, MultipartFile content, Integer fileId, String alias, String url) {

		CmsFile file = null;
		if (fileId != null) {
			file = CmsFile.findCmsFile(fileId);
		}

		boolean persistable = false;
		if (file == null) {
			file = new CmsFile();
			persistable = true;
		}

		String oldFilename = file.getFilename();

		file.setLabel(alias);
		file.setFilename(content.getOriginalFilename());
		file.setContentType(content.getContentType());
		file.setFilesize(content.getSize());
		file.setLabel(alias);
		file.setUrl(url);

		// TODO Cosmin: check the use-case: file with the same name is added to
		// the same folder
		// should overwrite the existing row in DB, OR throw ERROR

		CmsFolder parentFolder = CmsFolder.findCmsFolder(folderId);

		if (parentFolder == null) {
			return new AdminJson(false, "CMS File not created because this Folder ID is invalid: " + folderId);
		}

		if (parentFolder.getCmsFiles() != null && parentFolder.getCmsFiles().contains(file)) {
			// do nothing
		} else {
			if (parentFolder.getCmsFiles() == null) {
				parentFolder.setCmsFiles(new HashSet<CmsFile>());
				parentFolder.getCmsFiles().add(file);
			} else {
				parentFolder.getCmsFiles().add(file);
			}
			CmsFolder.entityManager().persist(parentFolder);
		}
		file.setCmsFolderId(parentFolder);

		if (persistable) {
			CmsFile.entityManager().persist(file);
		} else {
			CmsFile.entityManager().merge(file);
		}
		// return new AdminJson(false, "CMS File was not created. Exception: " +
		// e.getMessage());

		return new AdminJson(true, "CMS File created successfully", file.getId());
	}

	public static AdminJson fileMove(Integer folderId, Integer fileId) {

		CmsFile file = null;
		if (fileId != null) {
			file = CmsFile.findCmsFile(fileId);
		}

		if (file == null) {
			return new AdminJson(false, "CMS File to be moved should exist");
		}

		CmsFolder folder = CmsFolder.findCmsFolder(folderId);

		if (folderId == null || folder == null) {
			return new AdminJson(false, "The CMS Folder should exist");
		}

		if (folderId == (file.getCmsFolderId() == null ? null : file.getCmsFolderId().getId())) {
			return new AdminJson(false, "The folder of the CMS File doesn't change");
		}

		try {
			if (folder != null) {
				if (folder.getCmsFiles() == null) {
					folder.setCmsFiles(new HashSet<CmsFile>());
					folder.getCmsFiles().add(file);
				} else {
					folder.getCmsFiles().add(file);
				}
				CmsFolder.entityManager().persist(folder);
			}
			file.setCmsFolderId(folder);

			CmsFile.entityManager().persist(file);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "File not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS File moved successfully");
	}

	public static AdminJson folderMove(Integer parentFolderId, Integer folderId) {

		CmsFolder folder = null;
		if (folderId != null) {
			folder = CmsFolder.findCmsFolder(folderId);
		}

		if (folder == null) {
			return new AdminJson(false, "CMS Folder to be moved should exist");
		}

		CmsFolder parentFolder = CmsFolder.findCmsFolder(parentFolderId);

		if (parentFolderId == null || parentFolder == null) {
			return new AdminJson(false, "The CMS Folder should exist");
		}

		if (parentFolderId == (folder.getParentId() == null ? null : folder.getParentId().getId())) {
			return new AdminJson(false, "The parent of the CMS Folder doesn't change");
		}

		try {
			if (parentFolder != null) {
				if (parentFolder.getCmsFolders() == null) {
					parentFolder.setCmsFolders(new HashSet<CmsFolder>());
					parentFolder.getCmsFolders().add(folder);
				} else {
					parentFolder.getCmsFolders().add(folder);
				}
				CmsFolder.entityManager().persist(parentFolder);
			}
			folder.setParentId(parentFolder);

			CmsFolder.entityManager().persist(folder);
		} catch (EntityExistsException e) {
			return new AdminJson(false, "CMS Folder not moved " + e.getMessage());
		}

		return new AdminJson(true, "CMS Folder moved successfully");
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private boolean success;

	private String message;

	private Integer id;

	public AdminJson(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public AdminJson(boolean success, String message, Integer id) {
		this.success = success;
		this.message = message;
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("id").serialize(this);
	}

	public String toJsonWithId() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

}
