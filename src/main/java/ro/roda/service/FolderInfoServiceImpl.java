package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.FolderInfo;

@Service
@Transactional
public class FolderInfoServiceImpl implements FolderInfoService {

	public List<FolderInfo> findAllFolderInfos() {
		return FolderInfo.findAllFolderInfos();
	}

	public FolderInfo findFolderInfo(Integer id) {
		return FolderInfo.findFolderInfo(id);
	}
}
