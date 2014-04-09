package ro.roda.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import java.util.HashSet;
import java.util.List;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

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
import ro.roda.ddi.RspStmtType;
import ro.roda.ddi.StdyDscrType;
import ro.roda.ddi.StdyInfoType;
import ro.roda.ddi.SubjectType;
import ro.roda.ddi.SumDscrType;
import ro.roda.ddi.TopcClasType;
import ro.roda.ddi.VarType;
import ro.roda.domain.*;
import ro.roda.filestore.CmsFileStoreService;
import ro.roda.service.CatalogService;
import ro.roda.service.CityService;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;
import ro.roda.service.FileService;
import ro.roda.service.StudyService;
import ro.roda.transformer.AdminJson;
import au.com.bytecode.opencsv.CSVReader;

@Service
@Transactional
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

	private static final String errorMessage = "Could not import data";

	private static final String elsstEnTerms = "elsst_en_terms.csv";

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
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsFileService cmsFileService;

	@Value("${roda.data.cms}")
	private String rodaDataCms;

	@Value("${roda.data.csv}")
	private String rodaDataCsv;

	@Value("${roda.data.csv-extra}")
	private String rodaDataCsvExtra;

	@Value("${roda.data.ddi}")
	private String rodaDataDdi;

	@Value("${roda.data.elsst}")
	private String rodaDataElsst;

	@Value("${roda.data.csv-after-ddi}")
	private String rodaDataCsvAfterDdi;

	@Value("${roda.data.ddi.xsd}")
	private static String xsdDdi122 = "xsd/ddi122.xsd";

	@Value("${roda.data.ddi.persist}")
	private String ddiPersist;

	@Value("${roda.data.ddi.files}")
	private String rodaDataDdiFiles;

	private Unmarshaller unmarshaller = null;

	public static final EntityManager entityManager() {
		EntityManager em = new ImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public final Unmarshaller getUnmarshaller() {

		if (unmarshaller == null) {
			// validate using DDI 1.2.2 XML Schema
			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			try {
				unmarshaller = JAXBContext.newInstance(jaxbContextPath).createUnmarshaller();
				unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
						xsdDdiRes.getFile()));
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return unmarshaller;
	}

	public void importAll() {
		log.trace("roda.data.csv = " + rodaDataCsv);
		log.trace("roda.data.csv-extra = " + rodaDataCsvExtra);
		log.trace("roda.data.ddi = " + rodaDataDdi);
		log.trace("roda.data.csv-after-ddi = " + rodaDataCsvAfterDdi);
		log.trace("roda.data.cms = " + rodaDataCms);

		// to skip the initial actions,
		// change properties to another string
		// (not "yes")
		if ("yes".equalsIgnoreCase(rodaDataCsv)) {
			importCsv();
			if ("yes".equalsIgnoreCase(rodaDataCsvExtra)) {
				importCsvExtra();
			}
		}

		if ("yes".equalsIgnoreCase(rodaDataElsst)) {
			importElsst();
		}

		if ("yes".equalsIgnoreCase(rodaDataDdi)) {
			importDdiFiles();
			if ("yes".equalsIgnoreCase(rodaDataCsvAfterDdi)) {
				importCsvAfterDdi();
			}
		}

		if ("yes".equalsIgnoreCase(rodaDataCms)) {
			importCms();
		}
	}

	public void importCms() {
		importCmsFiles("files");
		importCmsLayouts("layouts");
		importCmsPages("pages");
		importCmsSnippets("snippets");
	}

	public void importCmsFiles(String folderName) {
		try {
			Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
			File cmsDir = cmsRes.getFile();

			AdminJson result = AdminJson.folderSave(folderName, null, "CMS Files");
			CmsFolder cmsFolder = CmsFolder.findCmsFolder(result.getId());
			cmsFileStoreService.folderSave(cmsFolder);
			importCmsFilesRec(cmsDir, cmsFolder, rodaDataCmsDir + folderName);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private void importCmsFilesRec(File dir, CmsFolder cmsFolder, String path) {
		try {
			Tika tika = new Tika();
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
					// TODO set content-type to a real value
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
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void importCmsLayouts(String folderName) {
		try {
			Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
			File cmsDir = cmsRes.getFile();
			importCmsLayoutsRec(cmsDir, null);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private void importCmsLayoutsRec(File dir, CmsLayoutGroup cmsLayoutGroup) {
		try {
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
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void importCmsSnippets(String folderName) {
		try {
			Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
			File cmsDir = cmsRes.getFile();
			importCmsSnippetsRec(cmsDir, null);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private void importCmsSnippetsRec(File dir, CmsSnippetGroup cmsSnippetGroup) {
		try {
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
		} catch (IOException e) {
			log.error(e);
		}
	}

	public void importCmsPages(String folderName) {
		try {
			Resource cmsRes = new ClassPathResource(rodaDataCmsDir + folderName);
			File cmsDir = cmsRes.getFile();
			importCmsPagesRec(cmsDir, null, null);
		} catch (IOException e) {
			log.error(e);
		}
	}

	private void importCmsPagesRec(File dir, CmsPage cmsPage, CmsPage parent) {
		try {
			File[] files = dir.listFiles();
			List<File> directories = new ArrayList<File>();

			CmsPage p = null;
			for (File file : files) {
				// first, process the page.xml file; secondly, iterate through
				// the subdirectories

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
					CmsPageLang pLang = null;

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
									Lang lang = Lang.findLang(content.toLowerCase());
									if (lang != null) {
										pLang = new CmsPageLang();
										pLang.setLangId(lang);
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
							}
						}

					}

					// set the parent of the page
					p.setCmsPageId(parent);

					if (p.getUrl() == null || p.getUrl().trim().equals("")) {
						p.setUrl(processPageUrl(p.getName(), parent));
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

					// set the page language
					if (pLang != null) {
						pLang.setCmsPageId(p);
						pLang.setId(new CmsPageLangPK(pLang.getLangId().getId(), p.getId()));
						pLang.persist();

						Set<CmsPageLang> pageLangs = new HashSet<CmsPageLang>();
						pageLangs.add(pLang);
						p.setCmsPageLangId(pageLangs);
					}

				}
			}
			for (File directory : directories) {
				importCmsPagesRec(directory, null, p);
			}

		} catch (IOException e) {
			log.error(e);
		} catch (ParserConfigurationException e) {
			log.error(e);
		} catch (SAXException e) {
			log.error(e);
		}

	}

	private String processPageUrl(String pageTitle, CmsPage parent) {
		String result = null;
		if (pageTitle != null) {
			result = pageTitle.toLowerCase().trim();
			// replace (multiple) spaces with a (single) "-"
			result = result.replaceAll("\\s+", "-");
			result = result.replaceAll("-+", "-");
		}

		// generate a new name
		// TODO: if definitive, remove the following commented code OR generate
		// a new name relative to a full URL

		List<CmsPage> resultPages = CmsPage.findCmsPage(result);

		if (resultPages != null && resultPages.size() > 0) {
			int i = 1;
			while (CmsPage.findCmsPage(result + "_" + i) != null) {
				i++;
			}
			result = result + "_" + i;
		}

		return result;
	}

	public void importElsst() {
		CSVReader reader;
		List<String[]> csvLines;
		try {
			reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataElsstDir + elsstEnTerms).getFile()));
			csvLines = reader.readAll();
			for (String[] csvLine : csvLines) {
				log.trace("ELSST Term: " + csvLine[0]);
				Topic t = new Topic();
				t.setName(csvLine[0]);
				t.persist();
			}

			reader = new CSVReader(new FileReader(
					new ClassPathResource(rodaDataElsstDir + elsstEnRelationships).getFile()));
			csvLines = reader.readAll();
			for (String[] csvLine : csvLines) {
				log.trace("ELSST Relationship: " + csvLine[0] + " " + csvLine[1] + " " + csvLine[2]);
				Topic src = Topic.checkTopic(null, csvLine[0]);
				Topic dst = Topic.checkTopic(null, csvLine[2]);
				// log.trace("ELSST Terms: " + src.getName() + " " +
				// dst.getName());

				Set<Topic> topicSet = null;
				if (Integer.parseInt(csvLine[1]) == 5) {
					dst.setParentId(src);
					topicSet = src.getTopics();
					if (topicSet == null) {
						topicSet = new HashSet<Topic>();
					}
					topicSet.add(dst);
					src.setTopics(topicSet);
					// dst.merge();
					// src.merge();
					dst.merge(false);
					src.merge(false);
				}
				if (Integer.parseInt(csvLine[1]) == 8) {
					// TODO set related terms
				}
			}

		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		// flush all changes
		Topic.entityManager().flush();
	}

	public void importCsv() {
		importCsvDir(rodaDataCsvDir);
	}

	public void importCsvExtra() {
		importCsvDir(rodaDataCsvExtraDir);
	}

	public void importCsvAfterDdi() {
		CSVReader reader;
		try {
			// add Studies to Catalogs
			reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataCsvAfterDdiCatalogStudy).getFile()));
			List<String[]> csvLines;
			csvLines = reader.readAll();
			for (String[] csvLine : csvLines) {
				log.trace("Catalog " + csvLine[0] + " -> Study " + csvLine[1]);
				CatalogStudy cs = new CatalogStudy();
				cs.setId(new CatalogStudyPK(Integer.valueOf(csvLine[0]), Integer.valueOf(csvLine[1])));
				cs.persist();
			}

		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * Populates the database using data imported from a directory with CSV
	 * files (which are ordered by name).
	 */
	public void importCsvDir(String dirname) {
		log.trace("Importing CSV from directory: " + dirname);
		Connection con = null;
		try {
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
				try {
					log.trace("File: " + f.getAbsolutePath());

					// Postgresql requires a Reader for the COPY commands
					BufferedReader br = new BufferedReader(new FileReader(f));

					// read the first line, containing the enumeration of fields
					String tableFields = br.readLine();

					// obtain the table name from the file name
					String tableName = f.getName().substring(2, f.getName().length() - 4);

					// bulk COPY the remaining lines (CSV data)
					String copyQuery = "COPY " + tableName + "(" + tableFields + ") FROM stdin DELIMITERS ',' CSV";
					log.trace(copyQuery);
					cm.copyIn(copyQuery, br);
				} catch (Exception e) {
					log.error(e);
					throw new IllegalStateException(errorMessage);
				}
			}
		} catch (Exception e) {
			log.error(e);
			throw new IllegalStateException(errorMessage);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error(e);
					throw new IllegalStateException(errorMessage);
				}
			}
		}

		// update the FileStore to be synchronized with the DB
		for (CmsFolder cmsFolder : cmsFolderService.findAllCmsFolders()) {
			cmsFileStoreService.folderSave(cmsFolder);
		}
	}

	public void importDdiFiles() {
		try {
			log.trace("roda.data.ddi.files = " + rodaDataDdiFiles);

			this.getUnmarshaller();

			PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
			Resource[] resources = pmr.getResources("classpath*:" + rodaDataDdiFiles);
			if (resources.length == 0) {
				log.debug("No DDI files found for importing");
			}

			ArrayList<File> ddiFiles = new ArrayList<File>();
			for (Resource ddiResource : resources) {
				ddiFiles.add(ddiResource.getFile());
			}
			// sort files by name -> create predictable IDs for Studies
			Collections.sort(ddiFiles);

			for (File ddiFile : ddiFiles) {
				log.debug("Importing DDI file: " + ddiFile.getName());
				MockMultipartFile mockMultipartFile = new MockMultipartFile(ddiFile.getName(), ddiFile.getName(),
						"text/xml", new FileInputStream(ddiFile));
				CodeBook cb = (CodeBook) unmarshaller.unmarshal(ddiFile);
				importCodebook(cb, mockMultipartFile, true, true);
			}
			log.trace("Finished importing DDI files");

		} catch (IOException e) {
			log.error("IOException:", e);
			throw new IllegalStateException(errorMessage);
		} catch (JAXBException e) {
			log.error("JAXBException:", e);
			throw new IllegalStateException(errorMessage);
		}
	}

	public void importCodebook(CodeBook cb, MultipartFile multipartFile, boolean nesstarExported, boolean legacyDataRODA) {

		if ("yes".equalsIgnoreCase(ddiPersist)) {
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
				// TODO use the list
			}

			if (prodStmtType != null) {
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
			}
		}

		DataCollType dataCollType = null;
		if (stdyDscrType.getMethod().size() > 0) {
			dataCollType = stdyDscrType.getMethod().get(0).getDataColl().get(0);
		}

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

		// TODO replace user = admin ?
		Users u = Users.findUsers(1);
		s.setAddedBy(u);
		Set<Study> su = u.getStudies();
		su.add(s);
		u.setStudies(su);

		// TODO don't use directly ID = 1
		// TODO replace TimeMeth with a field in StudyDescr ?
		TimeMeth tm = TimeMeth.findTimeMeth(1);
		s.setTimeMethId(tm);
		Set<Study> tms = tm.getStudies();
		tms.add(s);
		tm.setStudies(tms);

		// TODO don't use directly ID = 1
		// TODO remove class UnitAnalysis ? (it is now a field in StudyDescr)
		UnitAnalysis ua = UnitAnalysis.findUnitAnalysis(1);
		s.setUnitAnalysisId(ua);
		Set<Study> uas = ua.getStudies();
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

		// Add the imported DDI File to current Study's Files (in File-Store)

		ro.roda.domain.File domainFile = new ro.roda.domain.File();
		fileService.saveFile(domainFile, multipartFile, false);

		HashSet<Study> sStudy = new HashSet<Study>();
		sStudy.add(s);
		domainFile.setStudies1(sStudy);
		domainFile.setContentType("application/xml");

		HashSet<ro.roda.domain.File> sFile = new HashSet<ro.roda.domain.File>();
		sFile.add(domainFile);
		s.setFiles1(sFile);

		domainFile.persist();

		// Add Study to an existing Catalog ("root" catalog)

		// TODO don't use directly ID='1' for Catalog
		CatalogStudy cs = new CatalogStudy();
		CatalogStudyPK csid = new CatalogStudyPK(1, s.getId());
		cs.setId(csid);
		cs.persist();

		// create a new Instance / Dataset for this import
		Instance instance = new Instance();
		instance.setStudyId(s);
		instance.setMain(true);
		// TODO replace user id = 1
		instance.setAddedBy(Users.findUsers(1));
		instance.setAdded(new GregorianCalendar());
		instance.persist();

		// import Variables
		Set<InstanceVariable> instanceVariableSet = new HashSet<InstanceVariable>();
		if (dataDscrType != null) {
			List<VarType> varTypeList = dataDscrType.getVar();
			int counter = 0;
			for (VarType varType : varTypeList) {
				log.trace("Variable = " + varType.getName());
				Variable variable = new Variable();
				variable.setName(varType.getName());
				if (varType.getLabl().size() > 0) {
					variable.setLabel(varType.getLabl().get(0).content);
				}
				// TODO check semantics
				variable.setVariableType((short) 0);
				// TODO check semantics
				variable.setType((short) 0);
				variable.persist();

				// Add categories names + their frequencies to
				// "other_statistics" table
				if (varType.getCatgry() != null) {
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

				InstanceVariable iv = new InstanceVariable();
				iv.setId(new InstanceVariablePK(instance.getId(), variable.getId()));
				iv.setOrderVariableInInstance(counter);
				counter++;
				iv.persist();
				instanceVariableSet.add(iv);
			}
		}
		instance.setInstanceVariables(instanceVariableSet);
		instance.merge();
	}

}
