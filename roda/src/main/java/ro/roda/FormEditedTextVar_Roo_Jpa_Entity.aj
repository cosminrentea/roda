// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import ro.roda.FormEditedTextVar;
import ro.roda.FormEditedTextVarPK;

privileged aspect FormEditedTextVar_Roo_Jpa_Entity {
    
    declare @type: FormEditedTextVar: @Entity;
    
    declare @type: FormEditedTextVar: @Table(schema = "public", name = "form_edited_text_var");
    
    @EmbeddedId
    private FormEditedTextVarPK FormEditedTextVar.id;
    
    public FormEditedTextVarPK FormEditedTextVar.getId() {
        return this.id;
    }
    
    public void FormEditedTextVar.setId(FormEditedTextVarPK id) {
        this.id = id;
    }
    
}