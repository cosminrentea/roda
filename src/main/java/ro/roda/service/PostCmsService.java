package ro.roda.service;

import ro.roda.domainjson.GroupSave;

public interface PostCmsService {

	public abstract GroupSave findGroupSave(String groupname, Integer parent, String description);

}
