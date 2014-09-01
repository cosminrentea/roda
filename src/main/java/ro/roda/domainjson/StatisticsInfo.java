package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import ro.roda.domain.Question;
import ro.roda.domain.Series;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.Variable;
import ro.roda.transformer.FieldNameTransformer;
import ro.roda.util.RBean;
import flexjson.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.RMainLoopCallbacks;
import org.rosuda.JRI.Rengine;

public class StatisticsInfo extends JsonInfo {

	private final Log log = LogFactory.getLog(this.getClass());

	private String operation;

	private Variable v1;

	private Variable v2;

	private REXP r;

	public StatisticsInfo() {

	}

	public StatisticsInfo(String operation, Long id1, Long id2) {
		this.v1 = Variable.findVariable(id1);
		this.v2 = Variable.findVariable(id2);
		this.operation = operation;
	}

	public String toJson() {
		// The data type REXP provides functions for converting
		// to different data types.
		// In this case we know that rnorm(X) must have returned
		// an array of doubles, so we know what to convert to
		double[] rnd = r.asDoubleArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rnd.length; i++) {
			sb.append(rnd[i]);
			sb.append(",");
		}

		return "{\"success\": true, \"data\":[" + sb + "]}";
	}

}