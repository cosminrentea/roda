package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Autowired
	DatabaseUtils du;

	@Autowired
	SolrUtils su;

	private final Log log = LogFactory.getLog(this.getClass());

	@Scheduled(cron="${roda.scheduler.cron.maintenance}")
	public void maintenance() {
//		log.trace("maintenance");
	}

	@Scheduled(cron="${roda.scheduler.cron.backup}")
	public void backup() {
//		log.trace("backup");
	}

	@Scheduled(cron="${roda.scheduler.cron.ping-solr}")
	public void pingSolr() {
//		log.trace("pingSolr");
//		su.pingSolr();
	}

	@Scheduled(cron="${roda.scheduler.cron.vacuum}")
	public void vacuum() {
//		log.trace("vacuum");
//		du.executeUpdate("VACUUM;");
	}

	public void vacuumAnalyze() {
//		log.trace("vacuumAnalyze");
//		du.executeUpdate("VACUUM(ANALYZE);");
	}

}
