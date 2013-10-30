package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.GroupSave;

@Service
@Transactional
public class PostCmsServiceImpl implements PostCmsService {

	public GroupSave findGroupSave(String groupname, Integer parent, String description) {
		return GroupSave.findGroupSave(groupname, parent, description);
	}
}
