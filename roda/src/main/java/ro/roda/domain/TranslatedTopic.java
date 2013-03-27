package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@Audited @RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = TranslatedTopicPK.class, versionField = "", table = "translated_topic", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class TranslatedTopic {
}
