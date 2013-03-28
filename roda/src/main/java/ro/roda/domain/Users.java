package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@Audited//(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "users", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSerializable
public class Users {
}
