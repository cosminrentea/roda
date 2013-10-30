package ro.roda.service;

import ro.roda.transformer.GroupSave;

public interface PostCmsService {

	public abstract GroupSave findGroupSave(String groupname, Integer parent, String description);

}
