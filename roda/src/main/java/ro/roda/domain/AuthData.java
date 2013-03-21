package ro.roda.domain;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooJpaActiveRecord(versionField = "", table = "auth_data", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooToString(excludeFields = { "users", "rodauser" })
public class AuthData {
}
