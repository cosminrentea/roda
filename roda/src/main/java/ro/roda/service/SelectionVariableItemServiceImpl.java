package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.SelectionVariableItem;
import ro.roda.domain.SelectionVariableItemPK;


@Service
@Transactional
public class SelectionVariableItemServiceImpl implements SelectionVariableItemService {

	public long countAllSelectionVariableItems() {
        return SelectionVariableItem.countSelectionVariableItems();
    }

	public void deleteSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        selectionVariableItem.remove();
    }

	public SelectionVariableItem findSelectionVariableItem(SelectionVariableItemPK id) {
        return SelectionVariableItem.findSelectionVariableItem(id);
    }

	public List<SelectionVariableItem> findAllSelectionVariableItems() {
        return SelectionVariableItem.findAllSelectionVariableItems();
    }

	public List<SelectionVariableItem> findSelectionVariableItemEntries(int firstResult, int maxResults) {
        return SelectionVariableItem.findSelectionVariableItemEntries(firstResult, maxResults);
    }

	public void saveSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        selectionVariableItem.persist();
    }

	public SelectionVariableItem updateSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        return selectionVariableItem.merge();
    }
}
