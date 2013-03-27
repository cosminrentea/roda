package ro.roda.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@Audited @RooJavaBean
@RooToString(excludeFields = { "parentId" })
@RooJpaActiveRecord(versionField = "", table = "catalog", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Catalog {

    @ManyToOne(optional=true)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ro.roda.domain.Catalog parentId;
    
}
