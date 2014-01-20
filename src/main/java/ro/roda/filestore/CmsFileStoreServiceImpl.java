package ro.roda.filestore;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import ro.roda.domain.CmsFolder;
import ro.roda.importer.ImporterService;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jcr.Repository;

import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Node;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.commons.JcrUtils;

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
			// JcrUtils.
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

	private static String getFullPath(CmsFile cmsFile) {
		String folderPath = getFullPath(cmsFile.getCmsFolderId());
		if (folderPath != null && !folderPath.endsWith("/")) {
			folderPath += "/";
		}
		return folderPath + cmsFile.getFilename();
	}

	private static String getFullPath(CmsFolder cmsFolder) {
		if (cmsFolder == null) {
			return null;
		}
		String fullPath = cmsFolder.getName();
		while (cmsFolder.getParentId() != null) {
			cmsFolder = cmsFolder.getParentId();
			fullPath = cmsFolder.getName() + "/" + fullPath;
		}
		return fullPath;
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

	public Map<String, String> getFileProperties(CmsFile cmsFile) {
		Map<String, String> result = new HashMap<String, String>();
		Session session;
		Property property;
		PropertyIterator pi;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				Node node = root.getNode(getFullPath(cmsFile));

				pi = node.getProperties();
				while (pi.hasNext()) {
					property = pi.nextProperty();
					result.put(property.getName(), property.getString());
				}

				// add all properties from "jcr:content" node
				// except the file content itself
				pi = node.getNode("jcr:content").getProperties();
				while (pi.hasNext()) {
					property = pi.nextProperty();
					if (!"jcr:data".equals(property.getName())) {
						result.put(property.getName(), property.getString());
					}
				}

				// log.trace(node.getPath());
				// log.trace(node.getProperty("message").getString());
			} finally {
				session.logout();
			}
		} catch (LoginException e) {
			log.error(e);
		} catch (RepositoryException e) {
			log.error(e);
		}
		return result;
	}

	public void fileSave(MultipartFile multipartFile, CmsFolder cmsFolder) {
		if (multipartFile == null || cmsFolder == null) {
			return;
		}
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				log.trace("> fileSave > move the file to the Repository");

				String fullPath = filestoreDir + "/" + multipartFile.getOriginalFilename();
				File f = new File(fullPath);
				multipartFile.transferTo(f);

				String folderName = getFullPath(cmsFolder);

				Node root = session.getRootNode();
				Node folderNode = null;
				try {
					folderNode = root.getNode(folderName);
				} catch (PathNotFoundException e) {
					try {
						folderNode = root.addNode(folderName, "nt:folder");
					} catch (Exception e2) {
						log.error(e2);
					}
				}

				if (folderNode != null) {
					Node fileNode = folderNode.addNode(multipartFile.getOriginalFilename(), "nt:file");
					// create the mandatory child node - jcr:content
					Node resNode = fileNode.addNode("jcr:content", "nt:resource");
					resNode.setProperty("jcr:data",
							session.getValueFactory().createBinary(new AutoCloseInputStream(new FileInputStream(f))));
					resNode.setProperty("jcr:mimeType", multipartFile.getContentType());
					session.save();
					updateSolrFile(f, multipartFile);
				}

			} catch (IllegalStateException e) {
				log.error(e);
			} catch (IOException e) {
				log.error(e);
			} finally {
				session.logout();
			}
		} catch (LoginException e1) {
			log.error(e1);
		} catch (RepositoryException e1) {
			log.error(e1);
		}
	}

	public void folderSave(CmsFolder cmsFolder) {
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				root.addNode(getFullPath(cmsFolder), "nt:folder");
				session.save();
			} catch (Exception e) {
				log.error(e);
			} finally {
				session.logout();
			}
		} catch (LoginException e1) {
			log.error(e1);
		} catch (RepositoryException e1) {
			log.error(e1);
		}
	}

	private InputStream fileLoad(String fileFullPath) {
		Session session;
		InputStream is = null;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				Node node = root.getNode(fileFullPath);
				is = node.getNode("jcr:content").getProperty("jcr:data").getValue().getBinary().getStream();
				log.trace(node.getPath());
				// log.trace(node.getProperty("message").getString());
				//
			} finally {
				session.logout();
			}
		} catch (LoginException e) {
			log.error(e);
		} catch (RepositoryException e) {
			log.error(e);
		}
		return is;
	}

	@Override
	public InputStream fileLoad(CmsFile cmsFile) {
		return fileLoad(getFullPath(cmsFile));
	}

	@Override
	public void folderEmpty(CmsFolder cmsFolder) {
		// TODO Cosmin
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				NodeIterator ni = root.getNode(getFullPath(cmsFolder)).getNodes();
				while (ni.hasNext()) {
					ni = (NodeIterator) ni.next();
					ni.remove();
				}
				root.getNode(getFullPath(cmsFolder)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public void folderDrop(CmsFolder cmsFolder) {
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				root.getNode(getFullPath(cmsFolder)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public void fileDrop(CmsFile cmsFile) {
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials("admin", new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				root.getNode(getFullPath(cmsFile)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public void fileMove(CmsFolder cmsFolder, CmsFile cmsFile) {
		// TODO Cosmin Auto-generated method stub
	}

	@Override
	public void folderMove(CmsFolder cmsFolderParent, CmsFolder cmsFolder) {
		// TODO Cosmin Auto-generated method stub
	}

}
