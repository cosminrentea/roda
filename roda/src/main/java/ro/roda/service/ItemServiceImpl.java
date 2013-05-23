package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Item;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	public long countAllItems() {
		return Item.countItems();
	}

	public void deleteItem(Item item) {
		item.remove();
	}

	public Item findItem(Long id) {
		return Item.findItem(id);
	}

	public List<Item> findAllItems() {
		return Item.findAllItems();
	}

	public List<Item> findItemEntries(int firstResult, int maxResults) {
		return Item.findItemEntries(firstResult, maxResults);
	}

	public void saveItem(Item item) {
		item.persist();
	}

	public Item updateItem(Item item) {
		return item.merge();
	}
}
