package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "acl_sid", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@Table(schema = "public", name = "acl_sid", uniqueConstraints = {@UniqueConstraint(columnNames={ "principal", "sid" })})
public class AclSid {

    @Column(name = "principal", columnDefinition = "bool")
    @NotNull
    private boolean principal;

    @Column(name = "sid", columnDefinition = "text")
    @NotNull
    private String sid;
}
