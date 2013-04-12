package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScheduledTask {

	private final Log log = LogFactory.getLog(this.getClass());

	public void maintenance() {
		//log.debug("> maintenance");
	}

	public void backup() {
		//log.debug("> backup");
	}

}
