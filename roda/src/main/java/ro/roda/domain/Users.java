package ro.roda.domain;

import java.io.Serializable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString(excludeFields = { "enabled", "password" })
@RooJpaActiveRecord(versionField = "", table = "users", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Users implements Serializable {

    private static final long serialVersionUID = -8774648400924010357L;
}
