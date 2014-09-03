package ro.roda.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Variable;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

	private final Log log = LogFactory.getLog(this.getClass());

	private Rengine re;

	private String rWorkingDirectory;

	private String rSourceFilename;

	private static final String classpathRodaR = "classpath*:R/roda.R";

	@PostConstruct
	private void postConstruct() {
		log.trace(System.getProperties());
		re = null;
		rWorkingDirectory = null;
		if (!"yes".equalsIgnoreCase(System.getProperty("jri.ignore.ule"))) {
			String[] args = new String[1];
			args[0] = "--vanilla";
			re = new Rengine(args, false, null);
			log.trace("JRI Rengine.versionCheck() = " + Rengine.versionCheck());

			// get R's working directory and R source file (as canonical paths)
			PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
			Resource[] resources;
			try {
				resources = pmr.getResources(classpathRodaR);
				if (resources.length == 1) {
					File rFile = resources[0].getFile();
					rSourceFilename = rFile.getCanonicalPath();
					rWorkingDirectory = rFile.getCanonicalFile().getParent();
				} else {
					log.error("roda.R : not found or ambiguous");
				}
			} catch (IOException e) {
				log.error("roda.R : not found");
			}
		}
	}

	@PreDestroy
	public void preDestroy() {
		if (re != null) {
			re.end();
		}
	}

	public String getStatisticsJson(String operation, List<Long> variableIds) {

		if (re != null && rWorkingDirectory != null && rSourceFilename != null) {
			re.eval("setwd(\"" + rWorkingDirectory + "\")");
			re.eval("source(\"" + rSourceFilename + "\")");
			REXP rexp = re
					.eval("getStats(list(vars = list(v1 = c(97, 99, sample(1:7, 122, replace=T), 99)), meta = list(v1 = c(\"Foarte putin\"=1, \"Foarte mult\"=7, \"Nu e cazul\"=97, \"Nu stiu\"=98, \"Nu raspund\"=99))))");
			if (rexp != null) {
				return rexp.asString();
			}
		}
		return "{\"success\": false, \"message\":\"Could not obtain statistics from R\"]}";
	}

	public String getStatisticsJsonDemo(String operation, Long id1, Long id2) {
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

		return "{\"success\": true, \"data\":[" + sbRnorm + "," + sbTable + "]}";

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