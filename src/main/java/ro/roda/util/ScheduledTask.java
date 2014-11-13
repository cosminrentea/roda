package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ro.roda.service.solr.SolrService;

@Component
public class ScheduledTask {

	@Autowired
	DatabaseUtils du;

	@Autowired
	SolrService ss;

	private final Log log = LogFactory.getLog(this.getClass());

	@Scheduled(cron = "${scheduler.cron.maintenance}")
	public void maintenance() {
		// log.trace("maintenance");
	}

	@Scheduled(cron = "${scheduler.cron.backup}")
	public void backup() {
		// log.trace("backup");
	}

	@Scheduled(cron = "${scheduler.cron.ping-solr}")
	public void pingSolr() {
		ss.ping();
	}

	@Scheduled(cron = "${scheduler.cron.vacuum}")
	public void vacuum() {
		// log.trace("vacuum");
		// du.executeUpdate("VACUUM(ANALYZE);");
	}

}
