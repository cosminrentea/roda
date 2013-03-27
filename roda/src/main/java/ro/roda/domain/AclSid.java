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
@RooJpaActiveRecord(versionField = "", table = "acl_sid", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class AclSid {

    @Column(name = "principal", columnDefinition = "bool")
    @NotNull
    private boolean principal;

    @Column(name = "sid", columnDefinition = "text")
    @NotNull
    private String sid;
}
