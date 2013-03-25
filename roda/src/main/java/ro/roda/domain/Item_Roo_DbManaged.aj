// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import ro.roda.domain.Item;
import ro.roda.domain.Scale;
import ro.roda.domain.SelectionVariableItem;
import ro.roda.domain.Value;

privileged aspect Item_Roo_DbManaged {
    
    @OneToOne(mappedBy = "item")
    private Scale Item.scale;
    
    @OneToOne(mappedBy = "item")
    private Value Item.value;
    
    @OneToMany(mappedBy = "itemId")
    private Set<SelectionVariableItem> Item.selectionVariableItems;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String Item.name;
    
    public Scale Item.getScale() {
        return scale;
    }
    
    public void Item.setScale(Scale scale) {
        this.scale = scale;
    }
    
    public Value Item.getValue() {
        return value;
    }
    
    public void Item.setValue(Value value) {
        this.value = value;
    }
    
    public Set<SelectionVariableItem> Item.getSelectionVariableItems() {
        return selectionVariableItems;
    }
    
    public void Item.setSelectionVariableItems(Set<SelectionVariableItem> selectionVariableItems) {
        this.selectionVariableItems = selectionVariableItems;
    }
    
    public String Item.getName() {
        return name;
    }
    
    public void Item.setName(String name) {
        this.name = name;
    }
    
}