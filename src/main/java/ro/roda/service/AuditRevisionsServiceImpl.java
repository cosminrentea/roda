package ro.roda.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.AuditRevisions;
import ro.roda.transformer.AuditRevisionsByDate;
import ro.roda.transformer.AuditRevisionsByObject;
import ro.roda.transformer.AuditRevisionsByUsername;
import ro.roda.transformer.AuditRevisionsInfo;
import ro.roda.transformer.AuditSimplifiedRevisionsByObject;
import ro.roda.transformer.AuditSimplifiedRevisionsByUsername;

@Service
@Transactional
public class AuditRevisionsServiceImpl implements AuditRevisionsService {

	public List<AuditRevisions> findAllRevisions() {
		return AuditRevisions.findAllRevisions();
	}

	public AuditRevisions findRevisions(Integer id) {
		return AuditRevisions.findRevision(id);
	}

	public List<AuditRevisionsInfo> findAllRevisionsInfo() {
		return AuditRevisionsInfo.findAllRevisionsInfo();
	}

	public AuditRevisionsInfo findRevisionsInfo(Integer id) {
		return AuditRevisionsInfo.findRevisionInfo(id);
	}

	public List<AuditRevisionsByObject> findAllAuditRevisionsByObject() {
		return AuditRevisionsByObject.findAllRevisionsByObject();
	}

	public AuditRevisionsByObject findAuditRevisionsByObject(String objectName) {
		return AuditRevisionsByObject.findRevisionsByObject(objectName);
	}

	public List<AuditRevisionsByDate> findAllAuditRevisionsByDate() {
		return AuditRevisionsByDate.findAllRevisionsByDate();
	}

	public AuditRevisionsByDate findAuditRevisionsByDate(Date date) {
		return AuditRevisionsByDate.findRevisionsByDate(date);
	}

	public List<AuditRevisionsByUsername> findAllAuditRevisionsByUsername() {
		return AuditRevisionsByUsername.findAllRevisionsByUsername();
	}

	public AuditRevisionsByUsername findAuditRevisionsByUsername(String username) {
		return AuditRevisionsByUsername.findRevisionsByUsername(username);
	}

	public List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject() {
		return AuditSimplifiedRevisionsByObject.findAllAuditSimplifiedRevisionsByObject();
	}

	public List<AuditSimplifiedRevisionsByObject> findAllAuditSimplifiedRevisionsByObject(String object) {
		return AuditSimplifiedRevisionsByObject.findAllAuditSimplifiedRevisionsByObject(object);
	}

	public AuditSimplifiedRevisionsByObject findAuditSimplifiedRevisionsByObject(String objectName, Integer id) {
		return AuditSimplifiedRevisionsByObject.findAuditSimplifiedRevisionsByObject(objectName, id);
	}

	public List<AuditSimplifiedRevisionsByUsername> findAllAuditSimplifiedRevisionsByUsername() {
		return AuditSimplifiedRevisionsByUsername.findAllAuditSimplifiedRevisionsByUsername();
	}

	public AuditSimplifiedRevisionsByUsername findAuditSimplifiedRevisionsByUsername(String username) {
		return AuditSimplifiedRevisionsByUsername.findAuditSimplifiedRevisionsByUsername(username);
	}

}
