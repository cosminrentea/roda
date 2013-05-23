package ro.roda.domain;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.plural.RooPlural;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "series", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "catalog" })
@RooSolrSearchable
@RooJson
@RooPlural("Serieses")
public class Series {
}
