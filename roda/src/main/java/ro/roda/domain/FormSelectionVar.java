package ro.roda.domain;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = FormSelectionVarPK.class, versionField = "", table = "form_selection_var", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class FormSelectionVar {
}
