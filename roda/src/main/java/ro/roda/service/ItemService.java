package ro.roda.service;

import java.util.List;

import ro.roda.domain.Item;

public interface ItemService {

	public abstract long countAllItems();

	public abstract void deleteItem(Item item);

	public abstract Item findItem(Long id);

	public abstract List<Item> findAllItems();

	public abstract List<Item> findItemEntries(int firstResult, int maxResults);

	public abstract void saveItem(Item item);

	public abstract Item updateItem(Item item);

}
