package ro.roda.domain;

import javax.persistence.Transient;
import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@Audited
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "file", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class File {

    @Transient
    private byte[] content;

    @Transient
    private String url;
}
