package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "person", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
@RooJson
public class Person {

    public boolean equals(Object o) {
        boolean r = false;
        if (o instanceof Person) {
            Person p = (Person) o;
            r = this.getFname().equalsIgnoreCase(p.getFname()) && this.getLname().equalsIgnoreCase(p.getLname());
        }
        return r;
    }
}
