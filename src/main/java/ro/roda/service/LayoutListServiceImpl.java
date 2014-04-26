package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.LayoutList;

@Service
@Transactional
public class LayoutListServiceImpl implements LayoutListService {

	public List<LayoutList> findAllLayoutLists() {
		return LayoutList.findAllLayoutLists();
	}

	public LayoutList findLayoutList(Integer id) {
		return LayoutList.findLayoutList(id);
	}
}
