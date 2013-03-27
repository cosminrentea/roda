package ro.roda.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@Audited @RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "acl_object_identity", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class AclObjectIdentity {

    @Column(name = "object_id_identity", columnDefinition = "int8")
    @NotNull
    private Long objectIdIdentity;
}
