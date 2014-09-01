package ro.roda.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Variable;
import ro.roda.domainjson.StatisticsInfo;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	private final Log log = LogFactory.getLog(this.getClass());

	private Rengine re;

	@SuppressWarnings("unused")
	@PostConstruct
	private void postConstruct() {
		log.trace(System.getProperties());
		if ("yes".equalsIgnoreCase(System.getProperty("jri.ignore.ule"))) {
			re = null;
		} else {
			String[] args = new String[1];
			args[0] = "--vanilla";
			re = new Rengine(args, false, null);
			log.trace("JRI Rengine.versionCheck() = " + Rengine.versionCheck());
		}
	}

	@SuppressWarnings("unused")
	@PreDestroy
	public void preDestroy() {
		if (re != null) {
			re.end();
		}
	}

	public String getStatisticsJson(String operation, Long id1, Long id2) {
		Variable v1 = Variable.findVariable(id1);
		Variable v2 = Variable.findVariable(id2);
		REXP rexp = re.eval("rnorm(" + id1.intValue() + ")");

		// The data type REXP provides functions for converting
		// to different data types.
		// In this case we know that rnorm(X) must have returned
		// an array of doubles, so we know what to convert to
		double[] rnd = rexp.asDoubleArray();
		StringBuilder sbRnorm = new StringBuilder();
		sbRnorm.append("{\"itemtype\" : \"paragraph\", \"title\" : \"rnorm\", \"content\":\"");
		for (int i = 0; i < rnd.length; i++) {
			sbRnorm.append(rnd[i]);
			sbRnorm.append(",");
		}
		sbRnorm.append("\"}");

		rexp = re.eval("table(c(1,3,1,2,4,1,5,2,3), c(2,1,1,2,1,1,1,2,1))");
		int[] table = rexp.asIntArray();
		StringBuilder sbTable = new StringBuilder();
		sbTable.append("{\"itemtype\" : \"paragraph\", \"title\" : \"table\", \"content\":\"");
		for (int i = 0; i < table.length; i++) {
			sbTable.append(table[i]);
			sbTable.append(",");
		}
		sbTable.append("\"}");

		rexp = re.eval("library(rjson)");
		rexp = re.eval("toJSON(table(c(1,3,1,2,4,1,5,2,3), c(2,1,1,2,1,1,1,2,1)))");
		String tablejson = rexp.asString();
		StringBuilder sbTableJson = new StringBuilder();
		sbTableJson.append("{\"itemtype\" : \"paragraph\", \"title\" : \"table as JSON\", \"content\":\"");
		sbTableJson.append(tablejson);
		sbTableJson.append("\"}");

		return "{\"success\": true, \"data\":[" + sbRnorm + "," + sbTable + "," + sbTableJson + "]}";

	}
}

/*
 * 1 variabila:
 * 
 * aa<-sample(18:90, 1200, replace=TRUE); summary(aa) //pt. numerice
 * 
 * bb<-sample(1:5, 1200, replace=TRUE); table(bb) // pt. categoriale
 * 
 * 2 variabile:
 * 
 * cc<-sample(1:2, 1200, replace=TRUE); table(bb,cc) // crosstab 2 variabile
 * categoriale
 */