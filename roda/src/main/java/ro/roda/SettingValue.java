package ro.roda;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "setting_value", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@RooToString(excludeFields = { "setting" })
public class SettingValue {
}
