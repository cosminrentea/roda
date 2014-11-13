package ro.roda.service.solr;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class SolrServiceImpl implements SolrService, SmartLifecycle, ApplicationContextAware {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	Environment env;

	ApplicationContext applicationContext = null;

	@Autowired(required = false)
	SolrServer solrServer;

	boolean solrOnline;

	@Value("${solr.compulsory}")
	private String solrCompulsory;

	public boolean deleteAll() {
		log.trace("> Solr deleteAll");
		boolean ret = false;
		if (ping()) {
			UpdateResponse ur = null;
			try {
				ur = solrServer.deleteByQuery("*:*", 0);
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
		solrOnline = false;
		if (solrServer != null) {
			try {
				SolrPingResponse resp = solrServer.ping();
				int status = resp.getStatus();
				if (status == 0) {
					solrOnline = true;
				} else {
					log.info(String.format("Solr ping returned status %s", status));
				}
			} catch (SolrServerException ex) {
				log.error("Solr ping threw exception", ex);
			} catch (IOException ex) {
				log.error("Solr ping threw exception", ex);
			}
			// TODO check this
			if (!solrOnline && ("yes".equalsIgnoreCase(solrCompulsory))) {
				((AbstractApplicationContext) applicationContext).close();
			}
		}
		return solrOnline;
	}

	@Override
	public void start() {
		log.trace("start()");

		// only when profile is
		// devel OR productioninit (initial import for production)
		if (env.acceptsProfiles("devel", "productioninit")) {
			if (deleteAll()) {
				log.info("Removed all Solr content");
			}
		}
	}

	@Override
	public void stop() {
		log.trace("stop()");
	}

	@Override
	public boolean isRunning() {
		return ping();
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
