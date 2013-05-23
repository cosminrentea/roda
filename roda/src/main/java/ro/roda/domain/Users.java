package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooDbManaged(automaticallyDelete = true)
@RooSerializable
@RooSolrSearchable
@RooJson
@RooJpaActiveRecord(versionField = "", table = "users", schema = "public", finders = { "findUsersesByUsernameLike", "findUsersesByUsernameLikeAndEnabled" })
public class Users {
}
