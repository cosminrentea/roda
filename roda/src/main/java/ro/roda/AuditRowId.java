package ro.roda;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = AuditRowIdPK.class, versionField = "", table = "audit_row_id", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class AuditRowId {
}
