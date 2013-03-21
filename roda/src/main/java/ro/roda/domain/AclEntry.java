package ro.roda.domain;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "acl_entry", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class AclEntry {

    @Column(name = "ace_order", columnDefinition = "int4")
    @NotNull
    private Integer aceOrder;
}
