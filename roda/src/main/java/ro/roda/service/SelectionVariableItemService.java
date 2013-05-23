package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.SelectionVariableItem;
import ro.roda.domain.SelectionVariableItemPK;

public interface SelectionVariableItemService {

	public abstract long countAllSelectionVariableItems();

	public abstract void deleteSelectionVariableItem(SelectionVariableItem selectionVariableItem);

	public abstract SelectionVariableItem findSelectionVariableItem(SelectionVariableItemPK id);

	public abstract List<SelectionVariableItem> findAllSelectionVariableItems();

	public abstract List<SelectionVariableItem> findSelectionVariableItemEntries(int firstResult, int maxResults);

	public abstract void saveSelectionVariableItem(SelectionVariableItem selectionVariableItem);

	public abstract SelectionVariableItem updateSelectionVariableItem(SelectionVariableItem selectionVariableItem);

}
