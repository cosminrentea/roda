package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.LayoutGroupInfo;

@Service
@Transactional
public class LayoutGroupInfoServiceImpl implements LayoutGroupInfoService {

	public List<LayoutGroupInfo> findAllLayoutGroupInfos() {
		return LayoutGroupInfo.findAllGroupInfos();
	}

	public LayoutGroupInfo findLayoutGroupInfo(Integer id) {
		return LayoutGroupInfo.findGroupInfo(id);
	}
}
