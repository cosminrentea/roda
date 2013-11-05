package ro.roda.service;

import java.util.List;

import ro.roda.transformer.LayoutList;

public interface LayoutListService {

	public abstract List<LayoutList> findAllLayoutLists();

	public abstract LayoutList findLayoutList(Integer id);

}
