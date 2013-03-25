// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.domain.CmsLayout;
import ro.roda.domain.CmsLayoutGroup;

privileged aspect CmsLayoutGroup_Roo_DbManaged {
    
    @OneToMany(mappedBy = "cmsLayoutGroupId")
    private Set<CmsLayout> CmsLayoutGroup.cmsLayouts;
    
    @OneToMany(mappedBy = "parentId")
    private Set<CmsLayoutGroup> CmsLayoutGroup.cmsLayoutGroups;
    
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CmsLayoutGroup CmsLayoutGroup.parentId;
    
    @Column(name = "name", columnDefinition = "varchar", length = 200)
    @NotNull
    private String CmsLayoutGroup.name;
    
    @Column(name = "description", columnDefinition = "text")
    private String CmsLayoutGroup.description;
    
    public Set<CmsLayout> CmsLayoutGroup.getCmsLayouts() {
        return cmsLayouts;
    }
    
    public void CmsLayoutGroup.setCmsLayouts(Set<CmsLayout> cmsLayouts) {
        this.cmsLayouts = cmsLayouts;
    }
    
    public Set<CmsLayoutGroup> CmsLayoutGroup.getCmsLayoutGroups() {
        return cmsLayoutGroups;
    }
    
    public void CmsLayoutGroup.setCmsLayoutGroups(Set<CmsLayoutGroup> cmsLayoutGroups) {
        this.cmsLayoutGroups = cmsLayoutGroups;
    }
    
    public CmsLayoutGroup CmsLayoutGroup.getParentId() {
        return parentId;
    }
    
    public void CmsLayoutGroup.setParentId(CmsLayoutGroup parentId) {
        this.parentId = parentId;
    }
    
    public String CmsLayoutGroup.getName() {
        return name;
    }
    
    public void CmsLayoutGroup.setName(String name) {
        this.name = name;
    }
    
    public String CmsLayoutGroup.getDescription() {
        return description;
    }
    
    public void CmsLayoutGroup.setDescription(String description) {
        this.description = description;
    }
    
}