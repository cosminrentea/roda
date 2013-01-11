// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import ro.roda.Email;
import ro.roda.InstanceOrg;
import ro.roda.Internet;
import ro.roda.Org;
import ro.roda.OrgAddress;
import ro.roda.OrgPrefix;
import ro.roda.OrgRelations;
import ro.roda.OrgSufix;
import ro.roda.PersonOrg;
import ro.roda.Source;
import ro.roda.StudyOrg;

privileged aspect Org_Roo_DbManaged {
    
    @OneToOne(mappedBy = "org")
    private Source Org.source;
    
    @OneToMany(mappedBy = "orgId")
    private Set<Email> Org.emails;
    
    @OneToMany(mappedBy = "orgId")
    private Set<InstanceOrg> Org.instanceOrgs;
    
    @OneToMany(mappedBy = "entityId")
    private Set<Internet> Org.internets;
    
    @OneToMany(mappedBy = "orgId")
    private Set<OrgAddress> Org.orgAddresses;
    
    @OneToMany(mappedBy = "org2Id")
    private Set<OrgRelations> Org.orgRelationss;
    
    @OneToMany(mappedBy = "org1Id")
    private Set<OrgRelations> Org.orgRelationss1;
    
    @OneToMany(mappedBy = "orgId")
    private Set<PersonOrg> Org.personOrgs;
    
    @OneToMany(mappedBy = "orgId")
    private Set<StudyOrg> Org.studyOrgs;
    
    @ManyToOne
    @JoinColumn(name = "org_prefix_id", referencedColumnName = "id", nullable = false)
    private OrgPrefix Org.orgPrefixId;
    
    @ManyToOne
    @JoinColumn(name = "org_sufix_id", referencedColumnName = "id", nullable = false)
    private OrgSufix Org.orgSufixId;
    
    @Column(name = "name", columnDefinition = "varchar", length = 100)
    @NotNull
    private String Org.name;
    
    @Column(name = "fullname", columnDefinition = "varchar", length = 100)
    @NotNull
    private String Org.fullname;
    
    public Source Org.getSource() {
        return source;
    }
    
    public void Org.setSource(Source source) {
        this.source = source;
    }
    
    public Set<Email> Org.getEmails() {
        return emails;
    }
    
    public void Org.setEmails(Set<Email> emails) {
        this.emails = emails;
    }
    
    public Set<InstanceOrg> Org.getInstanceOrgs() {
        return instanceOrgs;
    }
    
    public void Org.setInstanceOrgs(Set<InstanceOrg> instanceOrgs) {
        this.instanceOrgs = instanceOrgs;
    }
    
    public Set<Internet> Org.getInternets() {
        return internets;
    }
    
    public void Org.setInternets(Set<Internet> internets) {
        this.internets = internets;
    }
    
    public Set<OrgAddress> Org.getOrgAddresses() {
        return orgAddresses;
    }
    
    public void Org.setOrgAddresses(Set<OrgAddress> orgAddresses) {
        this.orgAddresses = orgAddresses;
    }
    
    public Set<OrgRelations> Org.getOrgRelationss() {
        return orgRelationss;
    }
    
    public void Org.setOrgRelationss(Set<OrgRelations> orgRelationss) {
        this.orgRelationss = orgRelationss;
    }
    
    public Set<OrgRelations> Org.getOrgRelationss1() {
        return orgRelationss1;
    }
    
    public void Org.setOrgRelationss1(Set<OrgRelations> orgRelationss1) {
        this.orgRelationss1 = orgRelationss1;
    }
    
    public Set<PersonOrg> Org.getPersonOrgs() {
        return personOrgs;
    }
    
    public void Org.setPersonOrgs(Set<PersonOrg> personOrgs) {
        this.personOrgs = personOrgs;
    }
    
    public Set<StudyOrg> Org.getStudyOrgs() {
        return studyOrgs;
    }
    
    public void Org.setStudyOrgs(Set<StudyOrg> studyOrgs) {
        this.studyOrgs = studyOrgs;
    }
    
    public OrgPrefix Org.getOrgPrefixId() {
        return orgPrefixId;
    }
    
    public void Org.setOrgPrefixId(OrgPrefix orgPrefixId) {
        this.orgPrefixId = orgPrefixId;
    }
    
    public OrgSufix Org.getOrgSufixId() {
        return orgSufixId;
    }
    
    public void Org.setOrgSufixId(OrgSufix orgSufixId) {
        this.orgSufixId = orgSufixId;
    }
    
    public String Org.getName() {
        return name;
    }
    
    public void Org.setName(String name) {
        this.name = name;
    }
    
    public String Org.getFullname() {
        return fullname;
    }
    
    public void Org.setFullname(String fullname) {
        this.fullname = fullname;
    }
    
}
