package ro.roda.service.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageContent;
import ro.roda.domain.CmsPageType;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.domain.Lang;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;
import ro.roda.service.CatalogService;
import ro.roda.service.CityService;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;
import ro.roda.service.StudyService;
import ro.roda.service.filestore.CmsFileStoreService;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ImporterServiceImpl implements ImporterService {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${roda.data.cms.dir}")
	private String rodaDataCmsDir;

	@Value("${roda.data.elsst.dir}")
	private String rodaDataElsstDir;

	private static final String elsstEnTerms = "elsst_terms.csv";

	private static final String elsstEnRelationships = "elsst_en_relationships.csv";

	private static final String pageXmlFile = "page.xml";

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
	CatalogService catalogService;

	@Autowired
	StudyService studyService;

	@Autowired
	CityService cityService;

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsFileService cmsFileService;

	private Tika tika = new Tika();

	public static final EntityManager entityManager() {
		EntityManager em = new ImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCmsFiles() throws IOException {

		// update the FileStore to be synchronized with the DB
		for (CmsFolder cmsFolder : cmsFolderService.findAllCmsFolders()) {
			cmsFileStoreService.folderSave(cmsFolder);
		}

		String folderName = "files";
		Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
		File cmsDir = cmsRes.getFile();

		AdminJson result = adminJsonService.folderSave(folderName, null, "CMS Files");
		CmsFolder cmsFolder = CmsFolder.findCmsFolder(result.getId());
		cmsFileStoreService.folderSave(cmsFolder);
		importCmsFilesRec(cmsDir, cmsFolder, rodaDataCmsDir + folderName);
	}

	private void importCmsFilesRec(File dir, CmsFolder cmsFolder, String path) throws FileNotFoundException,
			IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				CmsFolder newFolder = new CmsFolder();
				newFolder.setName(file.getName());
				newFolder.setParentId(cmsFolder);
				newFolder.persist();
				cmsFileStoreService.folderSave(newFolder);

				importCmsFilesRec(file, newFolder, path + "/" + file.getName());
			} else {
				MockMultipartFile mockMultipartFile = new MockMultipartFile(file.getName(), file.getName(),
						tika.detect(file), new FileInputStream(file));

				// TODO what is the alias of a file? for the moment, it is
				// its name (without extension)
				// TODO decide if the URL should be the one above (useful
				// for ImgLink) of this one:
				// "file://" + file.getAbsolutePath()
				adminJsonService.fileSave(cmsFolder.getId(), mockMultipartFile, null,
						file.getName().substring(0, file.getName().lastIndexOf(".")), path + "/" + file.getName());

				cmsFileStoreService.fileSave(mockMultipartFile, cmsFolder);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCmsLayouts() throws IOException {
		Resource cmsRes = new ClassPathResource(rodaDataCmsDir + "layouts");
		File cmsDir = cmsRes.getFile();
		importCmsLayoutsRec(cmsDir, null);
	}

	private void importCmsLayoutsRec(File dir, CmsLayoutGroup cmsLayoutGroup) throws FileNotFoundException, IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				AdminJson result = adminJsonService.layoutGroupSave(file.getName(),
						(cmsLayoutGroup != null) ? cmsLayoutGroup.getId() : null,
						(cmsLayoutGroup != null) ? cmsLayoutGroup.getDescription() : null);

				CmsLayoutGroup newLayoutGroup = CmsLayoutGroup.findCmsLayoutGroup(result.getId());
				importCmsLayoutsRec(file, newLayoutGroup);
			} else {
				adminJsonService.layoutSave((cmsLayoutGroup != null) ? cmsLayoutGroup.getId() : null,
						IOUtils.toString(new FileReader(file)), file.getName(), null, null);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCmsSnippets() throws IOException {
		Resource cmsRes = new ClassPathResource(rodaDataCmsDir + "snippets");
		File cmsDir = cmsRes.getFile();
		importCmsSnippetsRec(cmsDir, null);
	}

	private void importCmsSnippetsRec(File dir, CmsSnippetGroup cmsSnippetGroup) throws FileNotFoundException,
			IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				AdminJson result = adminJsonService.snippetGroupSave(file.getName(),
						(cmsSnippetGroup != null) ? cmsSnippetGroup.getId() : null, null);

				CmsSnippetGroup newSnippetGroup = CmsSnippetGroup.findCmsSnippetGroup(result.getId());
				importCmsSnippetsRec(file, newSnippetGroup);
			} else {
				adminJsonService.snippetSave((cmsSnippetGroup != null) ? cmsSnippetGroup.getId() : null,
						IOUtils.toString(new FileReader(file)), file.getName(), null);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCmsPages() throws IOException, ParserConfigurationException, SAXException {
		Resource cmsRes = new ClassPathResource(rodaDataCmsDir + "pages");
		File cmsDir = cmsRes.getFile();
		importCmsPagesRec(cmsDir, null, null);
	}

	private void importCmsPagesRec(File dir, CmsPage cmsPage, CmsPage parent) throws ParserConfigurationException,
			SAXException, IOException {
		File[] files = dir.listFiles();
		List<File> directories = new ArrayList<File>();

		CmsPage p = null;
		for (File file : files) {
			// first, process the page.xml file
			// secondly, iterate through all subdirectories

			if (file.isDirectory()) {
				directories.add(file);
			} else if (file.getName().equalsIgnoreCase(pageXmlFile)) {
				// we assume there is a "page.xml" file inside every folder

				// Get the DOM Builder Factory
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

				// Get the DOM Builder
				DocumentBuilder builder = factory.newDocumentBuilder();

				// Load and Parse the XML document
				// document contains the complete XML as a Tree.
				Document document = builder.parse(file);

				p = new CmsPage();
				CmsPageContent pContent = null;
				Lang lang = null;

				NodeList childNodes = document.getDocumentElement().getChildNodes();
				for (int j = 0; j < childNodes.getLength(); j++) {
					Node cNode = childNodes.item(j);
					if (cNode instanceof Element) {
						String content = null;
						if (cNode.getLastChild() instanceof CDATASection) {
							content = ((CDATASection) cNode.getLastChild()).getData();
						}
						if (cNode.getLastChild() instanceof Text) {
							content = cNode.getLastChild().getTextContent().trim();
						}

						switch (cNode.getNodeName()) {
						case "title":
							// TODO check if meaning of "title" = name
							p.setName(content);
							break;
						case "menutitle":
							p.setMenuTitle(content);
							break;
						case "url":
							p.setUrl(content);
							break;
						case "synopsis":
							p.setSynopsis(content);
							break;
						case "lang":
							// TODO set CmsPageLang - should we use the
							// check method instead of find?
							if (content != null) {
								lang = Lang.findLang(content.toLowerCase());
								if (lang != null) {
									p.setLangId(lang);
									Set<CmsPage> pages = lang.getCmsPages();
									if (pages == null) {
										pages = new HashSet<CmsPage>();
									}
									pages.add(p);
									lang.setCmsPages(pages);
									lang.merge();
								}
							}
							break;
						case "content":
							pContent = new CmsPageContent();
							pContent.setContentText(content);
							break;
						case "cacheable":
							p.setCacheable(Integer.parseInt(content));
							break;
						case "default_page":
							p.setDefaultPage(Boolean.parseBoolean(content));
							break;
						case "external_redirect":
							p.setExternalRedirect(content);
							break;
						case "internal_redirect":
							p.setInternalRedirect(content);
							break;
						case "navigable":
							p.setNavigable(Boolean.parseBoolean(content));
							break;
						case "searchable":
							p.setSearchable(Boolean.parseBoolean(content));
							break;
						case "published":
							p.setPublished(Boolean.parseBoolean(content));
							break;
						case "target":
							p.setTarget(content);
							break;
						case "visible":
							p.setVisible(Boolean.parseBoolean(content));
							break;
						case "cms_layout":
							p.setCmsLayoutId(CmsLayout.findCmsLayout(content));
							break;
						case "pagetype":
							p.setCmsPageTypeId(CmsPageType.checkCmsPageType(null, content, null));
							break;
						case "sequence":
							p.move(Integer.parseInt(content));
							break;
						}
					}
				}

				// set the parent of the page
				p.setCmsPageId(parent);

				p.setUrl(processPageUrl(p.getUrl(), p.getName(), parent));

				if (p.getLangId() == null) {
					lang = Lang.findLang("en");
					p.setLangId(lang);
					Set<CmsPage> pages = lang.getCmsPages();
					if (pages == null) {
						pages = new HashSet<CmsPage>();
					}
					pages.add(p);
					lang.setCmsPages(pages);
					lang.merge();
				}

				p.persist();

				// set the page content
				if (pContent != null) {
					pContent.setCmsPageId(p);
					pContent.persist();

					Set<CmsPageContent> pageContents = new HashSet<CmsPageContent>();
					pageContents.add(pContent);
					p.setCmsPageContents(pageContents);
				}
			}
		}

		for (File directory : directories) {
			importCmsPagesRec(directory, null, p);
		}
	}

	private String processPageUrl(String url, String pageTitle, CmsPage parent) {
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

		if (resultPage != null) {
			int i = 1;
			while (CmsPage.findCmsPageByParent(result + "_" + i, parent) != null) {
				i++;
			}
			result = result + "_" + i;
		}

		return result;
	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importElsst() throws Exception {
		log.trace("Importing ELSST");

		CSVReader reader = new CSVReader(new FileReader(
				new ClassPathResource(rodaDataElsstDir + elsstEnTerms).getFile()));
		List<String[]> csvLines = reader.readAll();
		reader.close();

		// an ad-hoc cache for the Topics
		Map<String, Topic> topicsMap = new HashMap<String, Topic>();

		// used for translations & original
		Lang roLang = Lang.findLang("ro");
		Integer roLangId = roLang.getId();
		Lang enLang = Lang.findLang("en");
		Integer enLangId = enLang.getId();

		TranslatedTopic tt;
		Set<TranslatedTopic> ttSet;

		for (String[] csvLine : csvLines) {

			// log.trace("ELSST Term: " + csvLine[0]);

			Topic t = new Topic();
			// t.setName(csvLine[0]);

			t.persist();

			// original - EN language
			if (csvLine.length >= 1 && csvLine[0] != null) {
				// log.trace("ELSST Term EN: " + csvLine[0]);
				tt = new TranslatedTopic();
				tt.setTranslation(csvLine[0].toUpperCase());
				tt.setId(new TranslatedTopicPK(enLangId, t.getId()));
				tt.persist();

				ttSet = t.getTranslations();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				t.setTranslations(ttSet);

				ttSet = enLang.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				enLang.setTranslatedTopics(ttSet);
			}

			// optional translation - RO language
			if (csvLine.length >= 2 && csvLine[1] != null) {
				// log.trace("ELSST Term RO: " + csvLine[1]);
				tt = new TranslatedTopic();
				tt.setTranslation(csvLine[1].toUpperCase());
				tt.setId(new TranslatedTopicPK(roLangId, t.getId()));
				tt.persist();

				ttSet = t.getTranslations();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				t.setTranslations(ttSet);

				ttSet = roLang.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				roLang.setTranslatedTopics(ttSet);
			}

			// put the Topic (EN) in the ad-hoc cache
			topicsMap.put(csvLine[0], t);

		}

		reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataElsstDir + elsstEnRelationships).getFile()));
		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {
			// log.trace("ELSST Relationship: " + csvLine[0] + " " + csvLine[1]
			// + " " + csvLine[2]);

			if (csvLine.length != 3) {
				String errorMessage = "ELSST Relationship is not correctly defined CSV: number of items per line";
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}

			Topic src = topicsMap.get(csvLine[0]);
			Topic dst = topicsMap.get(csvLine[2]);

			if (src == null || dst == null) {
				String errorMessage = "ELSST Relationship is not correctly defined in CSV: terms do not exist";
				log.error(errorMessage);

				// TODO uncomment this exception-throwing
				// only when ELSST Terms & Relationships are
				// stable and correlated !!!
				// throw new IllegalStateException(errorMessage);

				// temporary ignore this type of errors
				continue;
			}

			// set a parent - child relationship
			// (relationship type: 5 and 6)
			if (Integer.parseInt(csvLine[1]) == 5) {
				dst.setParentId(src);
				Set<Topic> topicSet = src.getTopics();
				if (topicSet == null) {
					topicSet = new HashSet<Topic>();
				}
				topicSet.add(dst);
				src.setTopics(topicSet);
				dst.merge(false);
				src.merge(false);
			}

			// set a top-term (parent => null)
			// (relationship type: 7)
			if (Integer.parseInt(csvLine[1]) == 7) {
				dst.setParentId(null);
			}

			// TODO set related terms
			// (relationship type: 8)
			if (Integer.parseInt(csvLine[1]) == 8) {
			}
		}
		log.trace("Finished Importing ELSST");
	}
}
