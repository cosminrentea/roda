package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Audited
@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "value", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "item" })
public class Value {
}
