package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@RooJson
@RooJpaActiveRecord(versionField = "", table = "address", schema = "public", finders = { "findAddressesByCityId", "findAddressesByPostalCodeEquals", "findAddressesByCityIdAndPostalCodeEquals" })
public class Address {
}
