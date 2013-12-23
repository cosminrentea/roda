package ro.roda.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.SolrPingResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SolrUtils implements ApplicationContextAware {

	ApplicationContext applicationContext = null;

	@Autowired(required = false)
	SolrServer solrServer;

	@Value("${solr.compulsory}")
	private String solrCompulsory;

	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void pingSolr() {
		log.trace("> pingSolr");
		if (solrServer != null) {
			boolean solrOnline = false;
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
			if (!solrOnline && ("yes".equalsIgnoreCase(solrCompulsory))) {
				((AbstractApplicationContext) applicationContext).close();
			}
		}
	}

	public boolean deleteAll() {
		log.trace("> deleteAll");
		boolean ret = false;
		if (solrServer != null) {
			UpdateResponse ur = null;
			try {
				ur = solrServer.deleteByQuery("*:*",0);
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

}
