package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Org;
import ro.roda.domain.Person;
import ro.roda.domain.Study;
import ro.roda.domain.StudyOrg;
import ro.roda.domain.StudyPerson;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorPrincipalInvestigator extends JsonInfo {

	public static String toJsonArray(Collection<DdiEditorPrincipalInvestigator> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.exclude("individual.forms", "individual.instancepeople", "individual.personAddresses",
				"individual.personEmails", "individual.personInternets", "individual.personLinkss",
				"individual.personOrgs", "individual.personPhones", "individual.prefixId", "individual.studypeople",
				"individual.suffixId", "individual.auditReader", "individual.classAuditReader");
		serializer.exclude("org.instanceOrgs", "org.orgAddresses", "org.orgEmails", "org.orgInternets",
				"org.orgPhones", "org.orgPrefixId", "org.orgRelationss", "org.orgRelationss1", "org.orgSufixId",
				"org.personOrgs", "org.shortName", "org.studyOrgs", "org.auditReader", "org.classAuditReader");
		serializer.exclude("keywords.studyKeywords", "keywords.auditReader", "keywords.classAuditReader");

		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorPrincipalInvestigator> findAllPrincipalInvestigators(Study study) {
		List<DdiEditorPrincipalInvestigator> result = null;

		if (study != null) {
			result = new ArrayList<DdiEditorPrincipalInvestigator>();

			Iterator<StudyOrg> itOrg = study.getStudyOrgs().iterator();
			while (itOrg.hasNext()) {
				StudyOrg studyOrg = itOrg.next();
				// TODO: This is a possible way to determine if someone is
				// investigator - to be discussed; what about "principal"
				// investigator?
				if (studyOrg.getAssoctypeId().getAssocName().equalsIgnoreCase("investigator")) {
					result.add(new DdiEditorPrincipalInvestigator(null, studyOrg.getOrgId()));
				}
			}

			Iterator<StudyPerson> itPers = study.getStudypeople().iterator();
			while (itPers.hasNext()) {
				StudyPerson studyPers = itPers.next();
				// TODO: See above + what about multiple affiliation?
				if (studyPers.getAssoctypeId().getAsocName().equalsIgnoreCase("investigator")) {
					Person p = studyPers.getPersonId();
					result.add(new DdiEditorPrincipalInvestigator(p, (p.getPersonOrgs() != null) ? p.getPersonOrgs().iterator()
							.next().getOrgId() : null));
				}
			}

		}

		return result;
	}

	public static DdiEditorPrincipalInvestigator findPrincipalInvestigator(Integer id, boolean individualInvestigator) {
		if (id == null)
			return null;

		Person person = null;
		Org organization = null;

		if (individualInvestigator == true) {
			person = Person.findPerson(id);
			// TODO: what if there are more organizations the person is
			// affiliated to? For now, only the first one is considered. Maybe a
			// "main" attribute needed for affliation.
			organization = (person.getPersonOrgs() != null) ? person.getPersonOrgs().iterator().next().getOrgId()
					: null;
		} else {
			organization = Org.findOrg(id);
		}

		return new DdiEditorPrincipalInvestigator(person, organization);
	}

	private final Log log = LogFactory.getLog(this.getClass());

	private Boolean individualInvestigator;

	Person individual;

	// the investigator organization or the affiliation of the individual
	// investigator, if any
	Org organization;

	public DdiEditorPrincipalInvestigator(Person individual, Org organization) {
		this.individual = individual;
		this.organization = organization;
		if (this.individual == null) {
			individualInvestigator = false;
		} else {
			individualInvestigator = true;
		}
	}

	public Person getIndividual() {
		return individual;
	}

	public Org getOrganization() {
		return organization;
	}

	public Boolean isIndividualInvestigator() {
		return individualInvestigator;
	}

	public void setIndividual(Person individual) {
		this.individual = individual;
		this.individualInvestigator = true;
	}

	public void setOrganization(Org organization) {
		this.organization = organization;
	}

	public void setIndividualInvestigator(Boolean individualInvestigator) {
		this.individualInvestigator = individualInvestigator;
	}

	public String toJson() {

		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");

		serializer.exclude("individual.forms", "individual.instancepeople", "individual.personAddresses",
				"individual.personEmails", "individual.personInternets", "individual.personLinkss",
				"individual.personOrgs", "individual.personPhones", "individual.prefixId", "individual.studypeople",
				"individual.suffixId", "individual.auditReader", "individual.classAuditReader");
		serializer.exclude("org.instanceOrgs", "org.orgAddresses", "org.orgEmails", "org.orgInternets",
				"org.orgPhones", "org.orgPrefixId", "org.orgRelationss", "org.orgRelationss1", "org.orgSufixId",
				"org.personOrgs", "org.shortName", "org.studyOrgs", "org.auditReader", "org.classAuditReader");
		serializer.exclude("keywords.studyKeywords", "keywords.auditReader", "keywords.classAuditReader");

		serializer.include("persons.id", "persons.lname", "persons.fname", "persons.mname");
		serializer.include("orgs.id", "orgs.fullName");
		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
