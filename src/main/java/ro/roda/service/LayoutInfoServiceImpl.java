package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.LayoutInfo;

@Service
@Transactional
public class LayoutInfoServiceImpl implements LayoutInfoService {

	public List<LayoutInfo> findAllLayoutInfos() {
		return LayoutInfo.findAllLayoutInfos();
	}

	public LayoutInfo findLayoutInfo(Integer id) {
		return LayoutInfo.findLayoutInfo(id);
	}
}
