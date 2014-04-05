package ro.roda.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ro.roda.transformer.AuditRevisions;
import ro.roda.transformer.AuditRevisionsByDate;
import ro.roda.transformer.AuditRevisionsByObject;
import ro.roda.transformer.AuditRevisionsByUsername;
import ro.roda.transformer.AuditRevisionsDate;
import ro.roda.transformer.AuditRevisionsInfo;
import ro.roda.transformer.AuditRevisionsObject;
import ro.roda.transformer.AuditRevisionsUser;
import ro.roda.transformer.AuditSimplifiedRevisionsByObject;
import ro.roda.transformer.AuditSimplifiedRevisionsByUsername;

public interface AuditRevisionsService {

	public abstract List<AuditRevisions> findAllRevisions();

	public abstract AuditRevisions findRevisions(Integer id);

	public abstract List<AuditRevisionsInfo> findAllRevisionsInfo();

	public abstract AuditRevisionsInfo findRevisionsInfo(Integer id);

	public abstract List<AuditRevisionsByObject> findAllAuditRevisionsByObject();

	public abstract AuditRevisionsByObject findAuditRevisionsByObject(String objectName);

	public abstract List<AuditRevisionsByDate> findAllAuditRevisionsByDate();

	public abstract AuditRevisionsByDate findAuditRevisionsByDate(Date date);

	public abstract List<AuditRevisionsByUsername> findAllAuditRevisionsByUsername();

	public abstract AuditRevisionsByUsername findAuditRevisionsByUsername(String username);

	public abstract List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject();

	public abstract List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject(String object);

	public abstract AuditSimplifiedRevisionsByObject findAuditSimplifiedRevisionsByObject(String objectName, Integer id);

	public abstract List<AuditSimplifiedRevisionsByUsername> findAllAuditSimplifiedRevisionsByUsername();

	public abstract AuditSimplifiedRevisionsByUsername findAuditSimplifiedRevisionsByUsername(String username);

	public abstract Set<AuditRevisionsObject> findAllAuditRevisionsObjects();

	public abstract Set<AuditRevisionsUser> findAllAuditRevisionsUsers();

	public abstract Set<AuditRevisionsDate> findAllAuditRevisionsDates();
}
