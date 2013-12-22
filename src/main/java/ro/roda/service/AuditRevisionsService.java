package ro.roda.service;

import java.util.Date;
import java.util.List;

import ro.roda.transformer.AuditRevisions;
import ro.roda.transformer.AuditRevisionsByDate;
import ro.roda.transformer.AuditRevisionsByObject;
import ro.roda.transformer.AuditRevisionsByUsername;
import ro.roda.transformer.AuditRevisionsInfo;

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

}
