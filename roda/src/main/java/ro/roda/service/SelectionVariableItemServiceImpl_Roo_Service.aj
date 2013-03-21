// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.SelectionVariableItem;
import ro.roda.domain.SelectionVariableItemPK;
import ro.roda.service.SelectionVariableItemServiceImpl;

privileged aspect SelectionVariableItemServiceImpl_Roo_Service {
    
    declare @type: SelectionVariableItemServiceImpl: @Service;
    
    declare @type: SelectionVariableItemServiceImpl: @Transactional;
    
    public long SelectionVariableItemServiceImpl.countAllSelectionVariableItems() {
        return SelectionVariableItem.countSelectionVariableItems();
    }
    
    public void SelectionVariableItemServiceImpl.deleteSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        selectionVariableItem.remove();
    }
    
    public SelectionVariableItem SelectionVariableItemServiceImpl.findSelectionVariableItem(SelectionVariableItemPK id) {
        return SelectionVariableItem.findSelectionVariableItem(id);
    }
    
    public List<SelectionVariableItem> SelectionVariableItemServiceImpl.findAllSelectionVariableItems() {
        return SelectionVariableItem.findAllSelectionVariableItems();
    }
    
    public List<SelectionVariableItem> SelectionVariableItemServiceImpl.findSelectionVariableItemEntries(int firstResult, int maxResults) {
        return SelectionVariableItem.findSelectionVariableItemEntries(firstResult, maxResults);
    }
    
    public void SelectionVariableItemServiceImpl.saveSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        selectionVariableItem.persist();
    }
    
    public SelectionVariableItem SelectionVariableItemServiceImpl.updateSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        return selectionVariableItem.merge();
    }
    
}