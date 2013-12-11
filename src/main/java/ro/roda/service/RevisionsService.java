package ro.roda.service;

import java.util.List;

import ro.roda.transformer.Revisions;
import ro.roda.transformer.RevisionsInfo;

public interface RevisionsService {

	public abstract List<Revisions> findAllRevisions();

	public abstract Revisions findRevisions(Integer id);

	public abstract List<RevisionsInfo> findAllRevisionsInfo();

	public abstract RevisionsInfo findRevisionsInfo(Integer id);

}
