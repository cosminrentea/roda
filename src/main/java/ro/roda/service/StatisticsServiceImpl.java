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

		if (variableIds != null && variableIds.size() > 0 && variableIds.size() <= 2 && re != null
				&& rWorkingDirectory != null && rSourceFilename != null) {

			re.eval("setwd(\"" + rWorkingDirectory + "\")");
			re.eval("source(\"" + rSourceFilename + "\")");

			log.trace("Statistics: rWorkingDirectory: " + rWorkingDirectory);
			log.trace("Statistics: rSourceFilename: " + rSourceFilename);
			log.trace("Statistics: Number of variables: " + variableIds.size());

			Variable v1 = null, v2 = null;
			String evalExpr = null;
			if (variableIds.size() == 1) {
				v1 = Variable.findVariable(variableIds.get(0));
				log.trace("v1: " + v1.getId());

				// exemplul 3 din roda.R
				// REXP rexp = re
				// .eval("getStats(list(vars = list(v1 = c(97, 99, sample(1:7, 122, replace=T), 99)), meta = list(v1 = c(\"Foarte putin\"=1, \"Foarte mult\"=7, \"Nu e cazul\"=97, \"Nu stiu\"=98, \"Nu raspund\"=99))))");

				// exemplul 5 din roda.R
				// REXP rexp = re
				// .eval("getStats(list(vars = list(v1 = c(97, 99, sample(1:10, 122, replace=T), 99), v2 = c(NA, sample(18:90, 123, replace = TRUE), 999)), meta = list(v1 = c(\"NR/NS\"=99, \"Nu e cazul\"=97), v2 = c(\"Non raspuns\"=999))))");

				evalExpr = "getStats(list(vars = list("
						+ v1.getName()
						+ " = c("
						+ v1.getValues()
						+ ")), meta = list("
						+ v1.getName()
						+ " = c(\"Mult mai buna\"=1, \"Mai buna\"=2, \"La fel\"=3, \"Mai proasta\"=4, \"Mult mai proasta\"=5, \"Nu e cazul\"=97, \"Nu stiu\"=98, \"Nu raspund\"=99))))";
			} else if (variableIds.size() == 2) {
				v1 = Variable.findVariable(variableIds.get(0));
				v2 = Variable.findVariable(variableIds.get(1));
				log.trace("v1: " + v1.getId());
				log.trace("v2: " + v2.getId());

				evalExpr = "getStats(list(vars = list("
						+ v1.getName()
						+ " = c("
						+ v1.getValues()
						+ "),"
						+ v2.getName()
						+ " = c("
						+ v2.getValues()
						+ ")), meta = list("
						+ v1.getName()
						+ " = c(\"Mult mai buna\"=1, \"Mai buna\"=2, \"La fel\"=3, \"Mai proasta\"=4, \"Mult mai proasta\"=5, \"Nu e cazul\"=97, \"Nu stiu\"=98, \"Nu raspund\"=99),"
						+ v2.getName()
						+ " = c(\"Mult mai buna\"=1, \"Mai buna\"=2, \"La fel\"=3, \"Mai proasta\"=4, \"Mult mai proasta\"=5, \"Nu e cazul\"=97, \"Nu stiu\"=98, \"Nu raspund\"=99))))";

			}

			log.trace("Statistics: Eval query / R expression: " + evalExpr);
			REXP rexp = re.eval(evalExpr);

			if (rexp != null) {
				return rexp.asString();
			} else {
				log.trace("Statistics: an incorrect R expression was generated and evaluated ?");
			}
		} else {
			log.trace("Statistics: incorrect setup or called incorrectly ?");
		}
		return "{\"success\": false, \"message\":\"ERROR (caused by R setup, parameters, generated expression, or evaluation). Could not obtain statistics from R\"}";
	}

	public String getStatisticsJsonDemo(Long rnormParam) {

		REXP rexp = re.eval("rnorm(" + rnormParam.intValue() + ")");

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