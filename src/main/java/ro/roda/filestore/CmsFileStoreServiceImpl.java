package ro.roda.filestore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;
import ro.roda.domain.CmsFile;
import ro.roda.importer.ImporterService;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jcr.Repository;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.RepositoryImpl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CmsFileStoreServiceImpl implements CmsFileStoreService {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	JackrabbitRepository repository;

	@Value("${R.filestore.dir}")
	private final static String filestoreDir = "/tmp";

	@Autowired(required = false)
	SolrServer solrServer;

	@Autowired
	ImporterService importer;

	@PostConstruct
	private void onStart() {
		try {
			log.debug("Jackrabbit initializing");
			log.debug("Jackrabbit initialized");
		} catch (Exception e) {
			log.error("Failed to initialize Jackrabbit", e);
		}
	}

	/**
	 * This method ensures clean shutdown of Jackrabbit on undeploy/shutdown
	 */
	@SuppressWarnings("unused")
	@PreDestroy
	private void shutdownJcr() {
		/*
		 * log.debug("Jackrabbit closing session"); session.logout();
		 * log.debug("Jackrabbit closed session");
		 */
		log.debug("Jackrabbit shutdown started");
		repository.shutdown();
		log.debug("Jackrabbit shutdown completed");
	}

	public long countAllCmsFiles() {
		return CmsFile.countCmsFiles();
	}

	public void deleteCmsFile(CmsFile file) {
		file.remove();
	}

	public List<CmsFile> findAllCmsFiles() {
		return CmsFile.findAllCmsFiles();
	}

	public CmsFile findCmsFile(Integer id) {
		return CmsFile.findCmsFile(id);
	}

	public List<CmsFile> findCmsFileEntries(int firstResult, int maxResults) {
		return CmsFile.findCmsFileEntries(firstResult, maxResults);
	}

	@Override
	public File loadFile(String fileFullPath) {
		Session session;
		try {
			session = repository.login();
			try {
				Node root = session.getRootNode();
				Node node = root.getNode(fileFullPath);
				System.out.println(node.getPath());
				System.out.println(node.getProperty("message").getString());
			} finally {
				session.logout();
			}

		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void removeCmsFile(CmsFile cmsFile) {
		Session session;
		try {
			session = repository.login();
			try {
				Node root = session.getRootNode();
				root.getNode("hello").remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveCmsFile(CmsFile cmsFile) {
		cmsFile.persist();
	}

	public void saveCmsFile(CmsFile cmsFile, MultipartFile multipartFile, String folder) {
		if (multipartFile != null) {
			Session session;
			try {
				session = repository.login();
				try {
					log.trace("> saveCmsFile > move the file to the Repository");

					String fullPath = filestoreDir + "/" + multipartFile.getOriginalFilename();
					File f = new File(fullPath);
					multipartFile.transferTo(f);

					Node root = session.getRootNode();

					Node folderNode = root.addNode("hello");
					Node fileNode = folderNode.addNode(multipartFile.getOriginalFilename(), "nt:file");
					// create the mandatory child node - jcr:content
					Node resNode = fileNode.addNode("jcr:content", "nt:resource");
					resNode.setProperty("jcr:data",
							session.getValueFactory().createBinary(new AutoCloseInputStream(new FileInputStream(f))));
					resNode.setProperty("jcr:mimeType", multipartFile.getContentType());
					Calendar lastModified = Calendar.getInstance();
					lastModified.setTimeInMillis(f.lastModified());
					resNode.setProperty("jcr:lastModified", lastModified);
					session.save();
					updateSolrFile(f, multipartFile);

					log.trace("> saveCmsFile > set properties");
					cmsFile.setFilename(multipartFile.getOriginalFilename());
					cmsFile.setFilesize(multipartFile.getSize());

					log.trace("> saveCmsFile > save JPA object");
					saveCmsFile(cmsFile);

				} catch (IllegalStateException e) {
					log.error(e);
				} catch (IOException e) {
					log.error(e);
				} finally {
					session.logout();
				}
			} catch (LoginException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (RepositoryException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public CmsFile updateCmsFile(CmsFile cmsFile) {
		return cmsFile.merge();
	}

	@Async
	private void updateSolrFile(File f, MultipartFile content) {
		log.trace("> updateSolrFile");
		if (solrServer != null) {
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
			try {

				// SOLRJ 4.x API
				up.addFile(f, content.getContentType());
				log.trace("content.getContentType() = " + content.getContentType());

				up.setParam("literal.id", content.getOriginalFilename());
				up.setParam("uprefix", "attr_");
				up.setParam("fmap.content", "attr_content");
				up.setAction(ACTION.COMMIT, true, true);

				log.trace("> updateSolrFile > sending file to Solr for metadata indexing");

				solrServer.request(up);

				log.trace("> updateSolrFile > sent to Solr for metadata indexing");

				// log.debug("> saveFile > querying Solr");
				// QueryResponse rsp = solrServer.query(new SolrQuery("id:" +
				// content.getOriginalFilename()));
				// log.trace(rsp);

			} catch (IOException e) {
				log.error(e);
			} catch (SolrServerException e) {
				log.error(e);
			}
		}
	}

}
