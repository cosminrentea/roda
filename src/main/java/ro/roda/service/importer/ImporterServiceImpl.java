package ro.roda.service.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.Tika;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import flexjson.JSONSerializer;

import ro.roda.ddi.AuthEntyType;
import ro.roda.ddi.CatgryType;
import ro.roda.ddi.CitationType;
import ro.roda.ddi.CodeBook;
import ro.roda.ddi.DataCollType;
import ro.roda.ddi.DataDscrType;
import ro.roda.ddi.DocDscrType;
import ro.roda.ddi.KeywordType;
import ro.roda.ddi.ProdPlacType;
import ro.roda.ddi.ProdStmtType;
import ro.roda.ddi.ProducerType;
import ro.roda.ddi.RspStmtType;
import ro.roda.ddi.StdyDscrType;
import ro.roda.ddi.StdyInfoType;
import ro.roda.ddi.SubjectType;
import ro.roda.ddi.SumDscrType;
import ro.roda.ddi.TopcClasType;
import ro.roda.ddi.VarType;
import ro.roda.domain.Address;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.CmsFolder;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;
import ro.roda.domain.CmsPage;
import ro.roda.domain.CmsPageContent;
import ro.roda.domain.CmsPageType;
import ro.roda.domain.CmsSnippetGroup;
import ro.roda.domain.Form;
import ro.roda.domain.FormEditedNumberVar;
import ro.roda.domain.FormEditedNumberVarPK;
import ro.roda.domain.FormEditedTextVar;
import ro.roda.domain.FormEditedTextVarPK;
import ro.roda.domain.Instance;
import ro.roda.domain.Keyword;
import ro.roda.domain.Lang;
import ro.roda.domain.Org;
import ro.roda.domain.OtherStatistic;
import ro.roda.domain.Person;
import ro.roda.domain.Question;
import ro.roda.domain.Series;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.TimeMeth;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.domain.UnitAnalysis;
import ro.roda.domain.Users;
import ro.roda.domain.Variable;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;
import ro.roda.service.CatalogService;
import ro.roda.service.CityService;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;
import ro.roda.service.FileService;
import ro.roda.service.StudyService;
import ro.roda.service.filestore.CmsFileStoreService;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ImporterServiceImpl implements ImporterService {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${database.username}")
	private String dbUsername;

	@Value("${database.password}")
	private String dbPassword;

	@Value("${database.url}")
	private String dbUrl;

	@Value("${roda.data.cms.dir}")
	private String rodaDataCmsDir;

	@Value("${roda.data.csv.dir}")
	private String rodaDataCsvDir;

	@Value("${roda.data.csv-extra.dir}")
	private String rodaDataCsvExtraDir;

	@Value("${roda.data.elsst.dir}")
	private String rodaDataElsstDir;

	@Value("${roda.data.csv-after-ddi.catalog_study}")
	private String rodaDataCsvAfterDdiCatalogStudy;

	@Value("${roda.data.csv-after-ddi.series_study}")
	private String rodaDataCsvAfterDdiSeriesStudy;

	private static final String elsstEnTerms = "elsst_terms.csv";

	private static final String elsstEnRelationships = "elsst_en_relationships.csv";

	private static final String jaxbContextPath = "ro.roda.ddi";

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
	FileService fileService;

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsFileService cmsFileService;

	private Tika tika = new Tika();

	@Value("${roda.data.ddi.csv}")
	private String rodaDataDdiCsv;

	@Value("${roda.data.ddi.save_json}")
	private String rodaDataDdiSaveJson;

	@Value("${roda.data.ddi.xsd}")
	private String xsdDdi122;

	@Value("${roda.data.ddi.persist}")
	private String rodaDataDdiPersist;

	@Value("${roda.data.ddi.files}")
	private String rodaDataDdiFiles;

	@Value("${roda.data.ddi.otherstatistic}")
	private String rodaDataDdiOtherStatistic;

	private Unmarshaller unmarshaller = null;

	public static final EntityManager entityManager() {
		EntityManager em = new ImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public final Unmarshaller getUnmarshaller() throws JAXBException, SAXException, IOException {
		if (unmarshaller == null) {
			// validate using DDI 1.2.2 XML Schema
			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			unmarshaller = JAXBContext.newInstance(jaxbContextPath).createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					xsdDdiRes.getFile()));
		}
		return unmarshaller;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCmsFiles() throws IOException {
		String folderName = "files";
		Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
		File cmsDir = cmsRes.getFile();

		AdminJson result = AdminJson.folderSave(folderName, null, "CMS Files");
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
				AdminJson.fileSave(cmsFolder.getId(), mockMultipartFile, null,
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
				AdminJson result = AdminJson.layoutGroupSave(file.getName(),
						(cmsLayoutGroup != null) ? cmsLayoutGroup.getId() : null,
						(cmsLayoutGroup != null) ? cmsLayoutGroup.getDescription() : null);

				CmsLayoutGroup newLayoutGroup = CmsLayoutGroup.findCmsLayoutGroup(result.getId());
				importCmsLayoutsRec(file, newLayoutGroup);
			} else {
				AdminJson.layoutSave((cmsLayoutGroup != null) ? cmsLayoutGroup.getId() : null,
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
				AdminJson result = AdminJson.snippetGroupSave(file.getName(),
						(cmsSnippetGroup != null) ? cmsSnippetGroup.getId() : null, null);

				CmsSnippetGroup newSnippetGroup = CmsSnippetGroup.findCmsSnippetGroup(result.getId());
				importCmsSnippetsRec(file, newSnippetGroup);
			} else {
				AdminJson.snippetSave((cmsSnippetGroup != null) ? cmsSnippetGroup.getId() : null, file.getName(),
						IOUtils.toString(new FileReader(file)), null);
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

		// used for RO translations
		Lang roLang = Lang.findLang("ro");
		Integer roLangId = roLang.getId();

		for (String[] csvLine : csvLines) {
			// log.trace("ELSST Term: " + csvLine[0]);
			Topic t = new Topic();
			t.setName(csvLine[0]);
			t.persist();

			// put Topic in the ad-hoc cache
			topicsMap.put(csvLine[0], t);

			// optional translation - RO language
			if (csvLine.length == 2 && csvLine[1] != null) {
				// log.trace("ELSST Term RO: " + csvLine[1]);
				TranslatedTopic tt = new TranslatedTopic();
				tt.setTranslation(csvLine[1]);
				tt.setId(new TranslatedTopicPK(roLangId, t.getId()));
				tt.persist();

				Set<TranslatedTopic> ttSet = t.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				t.setTranslatedTopics(ttSet);

				ttSet = roLang.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				roLang.setTranslatedTopics(ttSet);

			}
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

			// Topic src = Topic.checkTopic(null, );
			// Topic dst = Topic.checkTopic(null, csvLine[2]);

			Topic src = topicsMap.get(csvLine[0]);
			Topic dst = topicsMap.get(csvLine[2]);

			if (src == null || dst == null) {
				String errorMessage = "ELSST Relationship is not correctly defined in CSV: terms do not exist";
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}

			if (Integer.parseInt(csvLine[1]) == 5) {

				// add a parent - child relationship

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

			if (Integer.parseInt(csvLine[1]) == 8) {
				// TODO set related terms
			}
		}
		log.trace("Finished Importing ELSST");
	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCsv() throws Exception {
		importCsvDir(rodaDataCsvDir);
	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCsvExtra() throws SQLException, IOException {
		importCsvDir(rodaDataCsvExtraDir);
	}

	/**
	 * Populates the database using data imported from a directory with CSV
	 * files (which are ordered by name).
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	public void importCsvDir(String dirname) throws SQLException, IOException {
		log.trace("Importing CSV from directory: " + dirname);
		Connection con = null;
		Properties conProps = new Properties();
		conProps.put("user", this.dbUsername);
		conProps.put("password", this.dbPassword);
		con = DriverManager.getConnection(this.dbUrl, conProps);

		Resource csvRes = new ClassPathResource(dirname);
		File csvDir = csvRes.getFile();
		File[] csvFiles = csvDir.listFiles();

		// sort file list by file name, ascending
		Arrays.sort(csvFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f1.getName().compareTo(f2.getName());
			}
		});

		CopyManager cm = ((BaseConnection) con).getCopyAPI();
		for (File f : csvFiles) {
			log.trace("File: " + f.getAbsolutePath());

			// Postgresql requires a Reader for the COPY commands
			BufferedReader br = new BufferedReader(new FileReader(f));

			// read the first line, containing the enumeration of fields
			String tableFields = br.readLine();

			// obtain the table name from the file name
			String tableName = f.getName().substring(2, f.getName().length() - 4);

			// TODO de facut importul sa mearga cu UTF8 -
			// SET CLIENT ENCODING UTF8 ...

			// bulk COPY the remaining lines (CSV data)
			String copyQuery = "COPY " + tableName + "(" + tableFields + ") FROM stdin DELIMITERS ',' CSV";
			log.trace(copyQuery);

			cm.copyIn(copyQuery, br);
		}

		// update the FileStore to be synchronized with the DB
		for (CmsFolder cmsFolder : cmsFolderService.findAllCmsFolders()) {
			cmsFileStoreService.folderSave(cmsFolder);
		}
	}

	public void importDdiFiles() throws FileNotFoundException, IOException, JAXBException, SAXException {
		log.trace("roda.data.ddi.files = " + rodaDataDdiFiles);
		log.trace("roda.data.ddi.csv = " + rodaDataDdiCsv);
		log.trace("roda.data.ddi.persist = " + rodaDataDdiPersist);

		boolean ddiPersistance = "yes".equalsIgnoreCase(rodaDataDdiPersist);
		boolean importDdiCsv = "yes".equalsIgnoreCase(rodaDataDdiCsv);

		this.getUnmarshaller();

		PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
		Resource[] resources = pmr.getResources("classpath*:" + rodaDataDdiFiles);
		if (resources.length == 0) {
			log.warn("No DDI files found for importing");
		}

		List<File> ddiFiles = new ArrayList<File>();
		for (Resource ddiResource : resources) {
			ddiFiles.add(ddiResource.getFile());
		}
		// sort files by name -> create predictable IDs for Studies
		Collections.sort(ddiFiles);
		for (File ddiFile : ddiFiles) {
			log.debug(ddiFile.getName());
		}

		for (File ddiFile : ddiFiles) {
			try {
				log.debug("Importing DDI file: " + ddiFile.getName());
				MockMultipartFile mockMultipartFileDdi = new MockMultipartFile(ddiFile.getName(), ddiFile.getName(),
						"text/xml", new FileInputStream(ddiFile));
				CodeBook cb = (CodeBook) unmarshaller.unmarshal(ddiFile);

				// add CSV data (if any) to imported DDIs
				MockMultipartFile mockMultipartFileCsv = null;
				if (importDdiCsv) {
					// get CSV file name (RODA naming rules)
					String csvFilename = ddiFile.getName().split("_")[0].concat("_T.csv");
					// TODO @Value for "ddi" folder below
					Resource csvResource = pmr.getResource("classpath:ddi/" + csvFilename);
					FileInputStream fisCsv = null;
					fisCsv = new FileInputStream(csvResource.getFile());
					mockMultipartFileCsv = new MockMultipartFile(csvFilename, csvFilename, "text/csv", fisCsv);
				}
				importDdiFile(cb, mockMultipartFileDdi, true, true, ddiPersistance, mockMultipartFileCsv);

			} catch (Exception e) {
				log.fatal("Exception thrown when importing DDI: " + ddiFile.getName(), e);
				throw e;
			}
		}
		log.debug("Finished importing DDI files");
	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importDdiIntoCatalogsAndSeries() throws Exception {
		CSVReader reader = new CSVReader(new FileReader(
				new ClassPathResource(rodaDataCsvAfterDdiCatalogStudy).getFile()));
		List<String[]> csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {
			log.trace("Catalog: " + csvLine[0] + " -- Study Filename: " + csvLine[1]);
			Study study = Study.findFirstStudyWithFilename(csvLine[1]);
			if (study == null) {
				String errorMessage = "Study cannot be added to Catalog because its filename was not found: "
						+ csvLine[1];
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}
			CatalogStudy cs = new CatalogStudy();
			cs.setId(new CatalogStudyPK(Integer.valueOf(csvLine[0]), study.getId()));
			cs.persist();
		}
		log.debug("DDI files moved to Catalogs");

		reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataCsvAfterDdiSeriesStudy).getFile()));
		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {
			log.trace("Series: " + csvLine[0] + " -- Study Filename: " + csvLine[1]);
			Study study = Study.findFirstStudyWithFilename(csvLine[1]);
			if (study == null) {
				String errorMessage = "Study cannot be added to Series because its filename was not found: "
						+ csvLine[1];
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}
			Series series = null;

			// TODO add Study to Series here !

		}
		log.debug("DDI files moved to Series");

	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importDdiFile(CodeBook cb, MultipartFile multipartFileDdi, boolean nesstarExported,
			boolean legacyDataRODA, boolean ddiPersistence, MultipartFile multipartFileCsv)
			throws FileNotFoundException, IOException {

		if (ddiPersistence) {
			if (this.entityManager == null) {
				this.entityManager = entityManager();
			}
			this.entityManager.persist(cb);
		}

		DocDscrType docDscrType = null;
		if (cb.getDocDscr().size() > 0) {
			docDscrType = cb.getDocDscr().get(0);
		}

		DataDscrType dataDscrType = null;
		if (cb.getDataDscr().size() > 0) {
			dataDscrType = cb.getDataDscr().get(0);
		}

		StdyDscrType stdyDscrType = cb.getStdyDscr().get(0);
		StdyInfoType stdyInfoType = null;
		SumDscrType sumDscrType = null;
		CitationType citationType = null;
		if (stdyDscrType.getStdyInfo().size() > 0) {
			stdyInfoType = stdyDscrType.getStdyInfo().get(0);
			if (stdyInfoType.getSumDscr().size() > 0) {
				sumDscrType = stdyInfoType.getSumDscr().get(0);
			}
		}
		if (stdyDscrType.getCitation().size() > 0) {
			citationType = stdyDscrType.getCitation().get(0);
			RspStmtType rspStmtType = citationType.getRspStmt();
			ProdStmtType prodStmtType = citationType.getProdStmt();
			if (rspStmtType != null) {
				List<AuthEntyType> authEntyTypeList = rspStmtType.getAuthEnty();
				for (AuthEntyType authEntyType : authEntyTypeList) {
					if (authEntyType.content != null) {
						Person person = new Person();

						// TODO split name
						person.setFname(authEntyType.content.trim());
						person.setLname(authEntyType.content.trim());

						// TODO use affiliation to link Person with an Org
						// (using abbreviation)
						person.setMname(authEntyType.getAffiliation());

						person.persist();
					}
				}
			}

			if (prodStmtType != null) {
				List<ProducerType> producerTypeList = prodStmtType.getProducer();
				List<ProdPlacType> prodPlacTypeList = prodStmtType.getProdPlac();
				for (ProdPlacType prodPlacType : prodPlacTypeList) {
					log.trace("prodPlacType = " + prodPlacType.content);
					// TODO parse imported address
					// TODO check if address already exists
					Address address = new Address();
					// TODO replace id = 1
					address.setCityId(cityService.findCity(1));
					address.setAddress1(prodPlacType.content);
					address.setImported(prodPlacType.content);
					address.persist();
				}

				// for (ProducerType producerType : producerTypeList) {
				// log.trace("producerType = " + producerType.content);
				// if (producerType.content != null) {
				// Org org = new Org();
				// org.setFullName(producerType.content.trim().toUpperCase());
				// org.setShortName(producerType.getAbbr());
				//
				// org.persist();
				// }
				//
				// }

			}
		}

		DataCollType dataCollType = null;
		if (stdyDscrType.getMethod().size() > 0) {
			dataCollType = stdyDscrType.getMethod().get(0).getDataColl().get(0);
		}

		// create the new Study
		Study s = new Study();

		String title = cb.getStdyDscr().get(0).getCitation().get(0).getTitlStmt().getTitl().getContent();
		log.trace("Title = " + title);

		if (nesstarExported && legacyDataRODA) {
			Date dateStart = null, dateEnd = null;
			Integer yearStart = null, yearEnd = null;

			Pattern pattern = Pattern.compile("\\d{4}");
			Matcher matcher = pattern.matcher(title);
			if (matcher.find()) {
				yearStart = new Integer(matcher.group());
				yearEnd = yearStart;
				Calendar cal = Calendar.getInstance();
				cal.set(yearStart.intValue(), 0, 1, 0, 0, 0);
				dateStart = cal.getTime();
				cal.set(yearEnd.intValue(), 11, 31, 23, 59, 59);
				dateEnd = cal.getTime();
			}
			s.setYearStart(yearStart);
			s.setYearEnd(yearEnd);
			s.setDateStart(dateStart);
			s.setDateEnd(dateEnd);

			log.trace("yearStart = " + yearStart);
			log.trace("yearEnd = " + yearEnd);
			log.trace("dateStart = " + dateStart);
			log.trace("dateEnd = " + dateEnd);
		}
		s.setAdded(new GregorianCalendar());

		// TODO check these default values
		s.setAnonymousUsage(true);
		s.setDigitizable(true);
		s.setRawData(true);
		s.setRawMetadata(false);
		s.setInsertionStatus(0);
		s.setImportedFilename(multipartFileDdi.getName());

		// TODO replace user = admin ?
		Users u = Users.findUsers(1);
		s.setAddedBy(u);
		Set<Study> su = u.getStudies();
		if (su == null) {
			su = new HashSet<Study>();
		}
		su.add(s);
		u.setStudies(su);

		// TODO don't use directly ID = 1
		// TODO replace TimeMeth with a field in StudyDescr ?
		TimeMeth tm = TimeMeth.findTimeMeth(1);
		s.setTimeMethId(tm);
		Set<Study> tms = tm.getStudies();
		if (tms == null) {
			tms = new HashSet<Study>();
		}
		tms.add(s);
		tm.setStudies(tms);

		// TODO don't use directly ID = 1
		// TODO remove class UnitAnalysis ? (it is now a field in StudyDescr)
		UnitAnalysis ua = UnitAnalysis.findUnitAnalysis(1);
		s.setUnitAnalysisId(ua);
		Set<Study> uas = ua.getStudies();
		if (uas == null) {
			uas = new HashSet<Study>();
		}
		uas.add(s);
		ua.setStudies(uas);

		s.persist();

		StudyDescr sd = new StudyDescr();
		// TODO obtain Romanian language, don't use directly ID = 1
		sd.setId(new StudyDescrPK(new Integer(1), s.getId()));
		sd.setTitle(title);
		sd.setOriginalTitleLanguage(true);

		if (stdyInfoType != null && stdyInfoType.getAbstract().size() > 0) {
			sd.setAbstract1(stdyInfoType.getAbstract().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getGeogCover().size() > 0) {
			sd.setGeographicCoverage(sumDscrType.getGeogCover().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getGeogUnit().size() > 0) {
			sd.setGeographicUnit(sumDscrType.getGeogUnit().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getUniverse().size() > 0) {
			sd.setUniverse(sumDscrType.getUniverse().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getAnlyUnit().size() > 0) {
			sd.setAnalysisUnit(sumDscrType.getAnlyUnit().get(0).content);
		}
		if (dataCollType != null && dataCollType.getWeight().size() > 0) {
			sd.setWeighting(dataCollType.getWeight().get(0).content);
		}
		if (dataCollType != null && dataCollType.getResInstru().size() > 0) {
			sd.setResearchInstrument(dataCollType.getResInstru().get(0).content);
		}

		sd.persist();

		if (stdyInfoType != null) {
			SubjectType subjectType = stdyInfoType.getSubject();
			if (subjectType != null) {

				// Keywords
				List<KeywordType> keywordTypeList = subjectType.getKeyword();
				for (KeywordType keywordType : keywordTypeList) {
					log.trace("Keyword = " + keywordType.content);
					Keyword keyword = Keyword.checkKeyword(null, keywordType.content);
					StudyKeyword sk = new StudyKeyword();
					// TODO replace user id = 1
					sk.setId(new StudyKeywordPK(s.getId(), keyword.getId(), 1));
					sk.setAddedBy(u);
					sk.setAdded(new GregorianCalendar());
					sk.persist();
				}

				// Topics / ELSST
				List<TopcClasType> topcClasTypeList = subjectType.getTopcClas();
				Set<Topic> tSet = new HashSet<Topic>();
				for (TopcClasType topcClasType : topcClasTypeList) {
					log.trace("Topic = " + topcClasType.content);
					Topic topic = Topic.checkTopic(null, topcClasType.content);
					if (topic == null) {
						topic = new Topic();
						topic.setName(topcClasType.content);
						topic.setParentId(null);
						topic.persist();
					}
					tSet.add(topic);
					Set<Study> sStudy = topic.getStudies();
					if (sStudy == null) {
						sStudy = new HashSet<Study>();
					}
					sStudy.add(s);
					topic.setStudies(sStudy);
				}
				s.setTopics(tSet);
			}
		}

		// add Study to an existing Catalog ("root" catalog)

		// TODO don't use directly ID='1' for Catalog
		// CatalogStudy cs = new CatalogStudy();
		// CatalogStudyPK csid = new CatalogStudyPK(1, s.getId());
		// cs.setId(csid);
		// cs.persist();

		// create a new Instance / Dataset for this import
		Instance instance = new Instance();
		instance.setStudyId(s);
		instance.setMain(true);
		// TODO replace user id = 1
		instance.setAddedBy(Users.findUsers(1));
		instance.setAdded(new GregorianCalendar());
		instance.persist();

		// import Variables
		// Set<Variable> variableSet = new HashSet<Variable>();
		if (dataDscrType != null) {
			List<VarType> varTypeList = dataDscrType.getVar();

			int counterQstn = 0;
			for (VarType varType : varTypeList) {
				log.trace("Variable = " + varType.getName());
				Variable variable = new Variable();
				variable.setName(varType.getName());
				if (varType.getLabl() != null && varType.getLabl().size() > 0) {
					variable.setLabel(varType.getLabl().get(0).content);
				}

				if (varType.getQstn().size() > 0 && varType.getQstn().get(0).getQstnLitType().size() > 0) {

					Question q = null;

					// TODO never check if the question has been already
					// imported? - see if it is ok like that
					// q = Question.checkQuestion(null, variable.getName(),
					// instance.getId(), varType.getQstn().get(0)
					// .getQstnLitType().get(0).content);
					q = new Question();

					// label = the variable's label (due to RODA's data
					// files)
					q.setName(variable.getName());
					q.setStatement(varType.getQstn().get(0).getQstnLitType().get(0).content);

					// TODO How to set the order of question in instance?
					// For now, they are ordered as they enter in the
					// database
					q.setOrderInInstance(counterQstn++);

					// update the Variable reference
					Set<Variable> qVariables = q.getVariables();
					if (qVariables == null) {
						qVariables = new HashSet<Variable>();
					}
					qVariables.add(variable);
					q.setVariables(qVariables);

					// set the containing Instance
					q.setInstanceId(instance);
					Set<Question> iQuestions = instance.getQuestions();
					if (iQuestions == null) {
						iQuestions = new HashSet<Question>();
					}
					iQuestions.add(q);
					instance.setQuestions(iQuestions);

					q.persist();

					// set the Variable's question
					variable.setQuestionId(q);
				}

				// build a string representing the categories (if any),
				// which can be passed "as-constructed" to R later
				StringBuilder sbCategories = new StringBuilder();
				if (varType.getCatgry() != null && varType.getCatgry().size() > 0) {
					Iterator<CatgryType> iterCategories = varType.getCatgry().iterator();
					CatgryType catgryType = iterCategories.next();
					if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
						sbCategories.append("\"").append(catgryType.getLabl().get(0).content).append("\"=")
								.append(catgryType.getCatValu().content);
					}
					while (iterCategories.hasNext()) {
						catgryType = iterCategories.next();
						if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
							sbCategories.append(",\"").append(catgryType.getLabl().get(0).content).append("\"=")
									.append(catgryType.getCatValu().content);
						}
					}
				}
				variable.setCategories(sbCategories.toString());

				// TODO check semantics
				variable.setVariableType((short) 0);

				variable.setOrderInQuestion(1);

				// TODO Check the order of variable in question
				// variable.setOrderInQuestion(q.getVariables().size() + 1);

				variable.persist();

				// Add categories names + their frequencies to
				// "other_statistics" table
				if (rodaDataDdiOtherStatistic.equalsIgnoreCase("yes") && (varType.getCatgry() != null)) {
					for (CatgryType catgryType : varType.getCatgry()) {
						if (catgryType.getCatStat() != null
								&& catgryType.getCatStat().size() > 0
								&& (catgryType.getLabl() != null && catgryType.getLabl().size() > 0 || catgryType
										.getCatValu() != null)) {
							OtherStatistic os = new OtherStatistic();
							os.setVariableId(variable);
							os.setValue(new Float(catgryType.getCatStat().get(0).content));
							if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
								os.setName(catgryType.getLabl().get(0).content);
							} else {
								os.setName(catgryType.getCatValu().content);
							}
							os.persist();
						}
					}
				}
			}
			// instance.merge();
		}

		// Add the imported DDI File to current Study's Files
		// (in the OLD deprecated File-Store)

		// ro.roda.domain.File domainFile = new ro.roda.domain.File();
		// fileService.saveFile(domainFile, multipartFileDdi, false);
		//
		// HashSet<Study> sStudy = new HashSet<Study>();
		// sStudy.add(s);
		// domainFile.setStudies1(sStudy);
		// domainFile.setContentType("application/xml");
		//
		// HashSet<ro.roda.domain.File> sFile = new
		// HashSet<ro.roda.domain.File>();
		// sFile.add(domainFile);
		// s.setFiles1(sFile);
		//
		// domainFile.persist();

		// Add the imported DDI File to current Study's Files
		// (in CMS-File-Store)
		AdminJson ret = adminJsonService.folderSave(CmsFolder.importedCmsFolderName, null,
				"Folder for imported DDI files and related files");

		// 1. create sub-folder for each imported study
		AdminJson retDdi = adminJsonService.folderSave(s.getId().toString(), ret.getId(), null);

		// 2. save the DDI XML file
		adminJsonService.fileSave(retDdi.getId(), multipartFileDdi, null, null, null);

		// save the data/CSV file, if any
		// if (multipartFileCsv != null) {
		// adminJsonService.fileSave(retDdi.getId(), multipartFileCsv, null,
		// null, null);
		//
		// List<String[]> csvLines;
		// CSVReader reader = new CSVReader(new BufferedReader(new
		// InputStreamReader(
		// multipartFileCsv.getInputStream(), "UTF8")), '\t');
		// csvLines = reader.readAll();
		// reader.close();
		//
		// String[] headerLine;
		// List<Variable> variableList = new ArrayList<Variable>();
		// Iterator<String[]> iter = csvLines.iterator();
		// if (iter.hasNext()) {
		// headerLine = iter.next();
		// for (int i = 0; i < headerLine.length; i++) {
		// headerLine[i] = headerLine[i].split("\"")[0];
		//
		// for (Question question : instance.getQuestions()) {
		// for (Variable qVar : question.getVariables()) {
		// if (qVar.getName().equalsIgnoreCase(headerLine[i])) {
		// variableList.add(qVar);
		// break;
		// }
		// }
		// }
		// }
		// }
		// for (; iter.hasNext();) {
		// String[] csvLine = iter.next();
		// log.trace("Line items: " + csvLine.length);
		// Form form = new Form();
		// form.persist();
		// for (int i = 0; i < csvLine.length; i++) {
		// FormEditedTextVar fetv = new FormEditedTextVar();
		//
		// fetv.setFormId(form);
		// Set<FormEditedTextVar> sfetv = form.getFormEditedTextVars();
		// if (sfetv == null) {
		// sfetv = new HashSet<FormEditedTextVar>();
		// }
		// sfetv.add(fetv);
		// form.setFormEditedTextVars(sfetv);
		//
		// fetv.setVariableId(variableList.get(i));
		// sfetv = variableList.get(i).getFormEditedTextVars();
		// if (sfetv == null) {
		// sfetv = new HashSet<FormEditedTextVar>();
		// }
		// sfetv.add(fetv);
		// variableList.get(i).setFormEditedTextVars(sfetv);
		// variableList.get(i).merge();
		//
		// fetv.setText(csvLine[i]);
		//
		// fetv.setId(new FormEditedTextVarPK(variableList.get(i).getId(),
		// form.getId()));
		//
		// fetv.persist();
		// }
		// }
		//
		// }

		if (multipartFileCsv != null) {

			// save the DDI CSV file
			adminJsonService.fileSave(retDdi.getId(), multipartFileCsv, null, null, null);

			List<String[]> csvLines;
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(
					multipartFileCsv.getInputStream(), "UTF8")), '|');
			csvLines = reader.readAll();
			reader.close();

			for (Iterator<String[]> iter = csvLines.iterator(); iter.hasNext();) {
				String[] csvLine = iter.next();
				// log.trace("Line items: " + csvLine.length);
				if (csvLine.length > 1) {
					StringBuilder varValues = new StringBuilder();
					varValues.append(csvLine[1]);
					for (int i = 2; i < csvLine.length; i++) {
						varValues.append(',').append(csvLine[i]);
					}
					if (instance.getQuestions() != null) {
						for (Question question : instance.getQuestions()) {
							for (Variable var : question.getVariables()) {
								if (csvLine[0].equalsIgnoreCase(var.getName())) {
									var.setValues(varValues.toString());
									// var.merge();
									break;
								}
							}
						}
					} else {
						log.error("instance.getQuestions() is null");
					}
				}
			}
		}

		s.flush();

		// serialization of DDI XML as JSON - only if requested
		if ("yes".equalsIgnoreCase(rodaDataDdiSaveJson)) {
			String ddiJson = new JSONSerializer().exclude("*.class").deepSerialize(cb);
			File fileJson = File.createTempFile("roda-json", null);
			FileWriter fw = new FileWriter(fileJson);
			fw.write(ddiJson);
			fw.flush();
			fw.close();
			FileInputStream fisJson = new FileInputStream(fileJson);

			MockMultipartFile mmfJson = new MockMultipartFile(multipartFileDdi.getName().concat(".json"),
					multipartFileDdi.getName().concat(".json"), "application/json", fisJson);

			// save the JSON in FileStore, inside the study's folder
			adminJsonService.fileSave(retDdi.getId(), mmfJson, null, null, null);
			fisJson.close();
			// log.trace(new JSONSerializer().deepSerialize(cb));
		}

	}
}
