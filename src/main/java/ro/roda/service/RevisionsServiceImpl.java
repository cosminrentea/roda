package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.Revisions;
import ro.roda.transformer.RevisionsInfo;

@Service
@Transactional
public class RevisionsServiceImpl implements RevisionsService {

	public List<Revisions> findAllRevisions() {
		return Revisions.findAllRevisions();
	}

	public Revisions findRevisions(Integer id) {
		return Revisions.findRevision(id);
	}

	public List<RevisionsInfo> findAllRevisionsInfo() {
		return RevisionsInfo.findAllRevisionsInfo();
	}

	public RevisionsInfo findRevisionsInfo(Integer id) {
		return RevisionsInfo.findRevisionInfo(id);
	}
}
