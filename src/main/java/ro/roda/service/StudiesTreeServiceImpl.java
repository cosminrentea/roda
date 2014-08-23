package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.StudiesTree;

@Service
@Transactional
public class StudiesTreeServiceImpl implements StudiesTreeService {

	public List<StudiesTree> findAllStudiesTrees() {
		return StudiesTree.findAllStudiesTrees();
	}

	public StudiesTree findStudiesTree(Integer id) {
		return StudiesTree.findStudiesTree(id);
	}
}
