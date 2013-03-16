package ro.roda.domain;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@RooJpaActiveRecord(versionField = "", table = "acl_object_identity", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@Table(schema = "public", name = "acl_object_identity", uniqueConstraints = {@UniqueConstraint(columnNames={ "object_id_class", "object_id_identity" })})
public class AclObjectIdentity {

    @Column(name = "object_id_identity", columnDefinition = "int8")
    @NotNull
    private Long objectIdIdentity;

    @ManyToOne
    @JoinColumn(name = "parent_object", referencedColumnName = "id", insertable = false, updatable = false)
    private ro.roda.domain.AclObjectIdentity parentObject;
}
