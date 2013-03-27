package ro.roda.domain;

import org.hibernate.envers.Audited;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;


@Audited @RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = FormEditedNumberVarPK.class, versionField = "", table = "form_edited_number_var", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class FormEditedNumberVar {
}
