package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@RooJson
@RooJpaActiveRecord(table = "study", schema = "public")
@RooToString(excludeFields = { "timeMethType" })
public class Study {
}
