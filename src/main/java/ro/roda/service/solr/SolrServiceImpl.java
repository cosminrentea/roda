package ro.roda.service.solr;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.Setting;

@Service
@Transactional
public class SolrServiceImpl implements SolrService, SmartLifecycle, ApplicationContextAware, ServletContextAware {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	Environment env;

	ApplicationContext applicationContext = null;

	@Autowired(required = false)
	SolrServer solrServer;

	@Value("${solr.compulsory}")
	private String solrCompulsory;

	// @Value("${solr.serverUrl}")
	// private String solrUrl;
	//
	// public static final String solrCore = "/collection1/";

	private boolean started = false;

	private String contextPath;

	private Map<String, String> urls = null;

	public boolean deleteAll() {
		log.trace("deleteAll()");
		boolean ret = false;
		if (ping()) {
			// HttpSolrServer httpSolrServer = new HttpSolrServer(solrUrl +
			// solrCore);
			UpdateResponse ur = null;
			try {
				ur = solrServer.deleteByQuery("*:*");
				ur = solrServer.commit();
			} catch (SolrServerException e) {
				log.error("Solr threw exception", e);
			} catch (IOException e) {
				log.error("Solr threw exception", e);
			}
			if (ur != null && ur.getStatus() == 0) {
				ret = true;
			}
		}
		return ret;
	}

	public boolean ping() {
		boolean result = false;
		if (solrServer != null) {
			try {
				SolrPingResponse resp = solrServer.ping();
				int status = resp.getStatus();
				if (status == 0) {
					result = true;
				} else {
					log.info(String.format("Solr ping returned status %s", status));
				}
			} catch (SolrServerException ex) {
				log.error("Solr ping threw exception", ex);
			} catch (IOException ex) {
				log.error("Solr ping threw exception", ex);
			}
			// TODO check this
			if (!result && ("yes".equalsIgnoreCase(solrCompulsory))) {
				((AbstractApplicationContext) applicationContext).close();
			}
		}
		return result;
	}

	@Override
	public void start() {
		log.trace("start()");

		// DELETE ALL SOLR CONTENT only when the profile is
		// devel OR productioninit (initial import for production)
		if (env.acceptsProfiles("devel", "productioninit") && deleteAll()) {
			log.info("Deleted all Solr content");
		}
		started = true;
	}

	@Override
	public void stop() {
		log.trace("stop()");
		started = false;
	}

	@Override
	public boolean isRunning() {
		return (started && ping());
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

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setServletContext(ServletContext c) {
		contextPath = c.getContextPath();
	}

	public String baseUrl(String language) {
		if (urls == null) {
			// compute the base URLs only once, for all languages
			urls = new HashMap<String, String>();

			// TODO use the existing "contextPath" field
			// instead of Setting[baseurl]
			urls.put("ro", Setting.findSetting("baseurl").getValue()
					+ Setting.findSetting("ro_databrowser_url").getValue() + "#");
			urls.put("en", Setting.findSetting("baseurl").getValue()
					+ Setting.findSetting("en_databrowser_url").getValue() + "#");
		}
		return urls.get(language);
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
