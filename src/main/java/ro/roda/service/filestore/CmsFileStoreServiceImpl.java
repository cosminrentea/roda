package ro.roda.service.filestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.io.input.AutoCloseInputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.api.JackrabbitRepository;
import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;

@Service
@Transactional
public class CmsFileStoreServiceImpl implements CmsFileStoreService, SmartLifecycle {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${jackrabbit.config}")
	private String jackrabbitConfigFile = null;

	@Value("${jackrabbit.home}")
	private String jackrabbitHome = null;

	private final static String jackrabbitUser = "admin";

	private JackrabbitRepository repository = null;

	@Autowired
	Environment env;

	@Autowired(required = false)
	SolrServer solrServer;

	private static String getAbsPath(CmsFile cmsFile) {
		String folderPath = getAbsPath(cmsFile.getCmsFolderId());
		if (folderPath != null && !folderPath.endsWith("/")) {
			folderPath += "/";
		}
		return folderPath + cmsFile.getFilename();
	}

	// private static String getRelPath(CmsFile cmsFile) {
	// String folderPath = getRelPath(cmsFile.getCmsFolderId());
	// if (folderPath != null && !folderPath.endsWith("/")) {
	// folderPath += "/";
	// }
	// return folderPath + cmsFile.getFilename();
	// }

	private static String getAbsPath(CmsFolder cmsFolder) {
		if (cmsFolder == null) {
			return null;
		}
		String fullPath = cmsFolder.getName();
		while (cmsFolder.getParentId() != null) {
			cmsFolder = cmsFolder.getParentId();
			fullPath = cmsFolder.getName() + "/" + fullPath;
		}
		return "/" + fullPath;
	}

	private static String getRelPath(CmsFolder cmsFolder) {
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

	@Override
	public void deleteAll() {
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				for (NodeIterator ni = root.getNodes(); ni.hasNext();) {
					ni.nextNode().remove();
				}
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public Map<String, String> getFileProperties(CmsFile cmsFile) {
		Map<String, String> result = new HashMap<String, String>();
		Session session;
		Property property;
		PropertyIterator pi;
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			session = repository.login(adminCred);
			try {
				Node node = session.getNode(getAbsPath(cmsFile));

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

	@Override
	public void fileSave(MultipartFile multipartFile, CmsFolder cmsFolder) {
		if (multipartFile == null || cmsFolder == null) {
			return;
		}
		log.trace("fileSave(): name = " + multipartFile.getOriginalFilename());
		log.trace("fileSave(): mime-type = " + multipartFile.getContentType());
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				Node folderNode = session.getNode(getAbsPath(cmsFolder));

				// save as a temporary file
				File f = File.createTempFile("roda-", null);
				multipartFile.transferTo(f);

				Node fileNode = folderNode.addNode(multipartFile.getOriginalFilename(), "nt:file");
				// create the mandatory child node - jcr:content
				Node resNode = fileNode.addNode("jcr:content", "nt:resource");
				resNode.setProperty("jcr:data",
						session.getValueFactory().createBinary(new AutoCloseInputStream(new FileInputStream(f))));
				resNode.setProperty("jcr:mimeType", multipartFile.getContentType());
				session.save();

				// updateSolrFile(f, multipartFile);

			} catch (Exception e) {
				log.error(e);
			} finally {
				session.logout();
			}
		} catch (Exception e1) {
			log.error(e1);
		}
	}

	@Override
	public void folderSave(CmsFolder cmsFolder) {
		Session session;
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			session = repository.login(adminCred);
			try {
				Node root = session.getRootNode();
				String relPath = getRelPath(cmsFolder);
				log.trace("folderSave(): relPath: " + relPath);
				root.addNode(relPath, "nt:folder");
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

	@Override
	public InputStream fileLoad(CmsFile cmsFile) {
		InputStream is = null;
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				Node node = session.getNode(getAbsPath(cmsFile));
				is = node.getNode("jcr:content").getProperty("jcr:data").getValue().getBinary().getStream();
				log.trace("fileLoad: " + node.getPath());
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

	/**
	 * Removes all files and sub-folders inside a folder. Keeps the folder.
	 */
	@Override
	public void folderEmpty(CmsFolder cmsFolder) {
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				for (NodeIterator ni = session.getRootNode().getNode(getAbsPath(cmsFolder)).getNodes(); ni.hasNext();) {
					ni.nextNode().remove();
				}
				// root.getNode(getFullPath(cmsFolder)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Removes a folder including all its children (files and sub-folders).
	 */
	@Override
	public void folderDrop(CmsFolder cmsFolder) {
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				session.getNode(getAbsPath(cmsFolder)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Removes a single file.
	 */
	@Override
	public void fileDrop(CmsFile cmsFile) {
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				session.getNode(getAbsPath(cmsFile)).remove();
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * Moves a single file to a different folder.
	 */
	public void fileMove(CmsFolder cmsFolder, CmsFile cmsFile) {
		try {
			SimpleCredentials adminCred = new SimpleCredentials(jackrabbitUser, new char[0]);
			Session session = repository.login(adminCred);
			try {
				session.move(getAbsPath(cmsFile), getAbsPath(cmsFolder) + "/" + cmsFile.getFilename());
				session.save();
			} finally {
				session.logout();
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void folderMove(CmsFolder cmsFolderParent, CmsFolder cmsFolder) {
		// TODO Cosmin
		// Auto-generated method stub
	}

	@Override
	public void start() {
		try {
			log.trace("start()");
			log.trace("config file: " + jackrabbitConfigFile);
			log.trace("home directory: " + jackrabbitHome);

			// create Repository using RespositoryConfig
			// config file is searched for in Classpath
			// (to allow relative paths in the property value)
			RepositoryConfig repositoryConfig = RepositoryConfig.create(new ClassPathResource(jackrabbitConfigFile)
					.getFile().getCanonicalPath(), jackrabbitHome);
			repository = RepositoryImpl.create(repositoryConfig);

			// remove everything from the repository only when profile is
			// devel OR productioninit (initial import for production)
			if (env.acceptsProfiles("devel", "productioninit")) {
				log.trace("Deleting everything in the repository");
				deleteAll();
			}
			log.trace("Jackrabbit repository initialized");
		} catch (Exception e) {
			log.fatal("Jackrabbit repository initialization exception: ", e);
			repository = null;
		}
	}

	@Override
	public void stop() {
		log.trace("stop()");
		if (repository != null) {
			log.trace("Jackrabbit repository - shutdown started");
			repository.shutdown();
			log.trace("Jackrabbit repository - shutdown completed");
			repository = null;
		}
	}

	@Override
	public boolean isRunning() {
		return (repository != null);
	}

	@Override
	public int getPhase() {
		return Integer.MIN_VALUE + 1;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		log.trace("stop(callback)");

		stop();

		// Our Stop/Shutdown is complete.
		// Regular shutdown will continue.
		callback.run();
	}
}
