package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.YearsTree;

@Service
@Transactional
public class YearsTreeServiceImpl implements YearsTreeService {

	public List<YearsTree> findAllYearsTree() {
		return YearsTree.findAllYearsTree();
	}

	public YearsTree findYearsTree(Integer id) {
		return YearsTree.findYearsTree(id);
	}
}
