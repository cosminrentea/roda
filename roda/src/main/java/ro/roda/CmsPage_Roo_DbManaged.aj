// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import ro.roda.CmsLayout;
import ro.roda.CmsPage;
import ro.roda.CmsPageContent;
import ro.roda.User;

privileged aspect CmsPage_Roo_DbManaged {
    
    @OneToMany(mappedBy = "page")
    private Set<CmsPageContent> CmsPage.cmsPageContents;
    
    @ManyToOne
    @JoinColumn(name = "layout", referencedColumnName = "id", nullable = false)
    private CmsLayout CmsPage.layout;
    
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id", nullable = false)
    private User CmsPage.owner;
    
    @Column(name = "name", columnDefinition = "varchar", length = 200)
    @NotNull
    private String CmsPage.name;
    
    @Column(name = "page_type", columnDefinition = "int4")
    @NotNull
    private Integer CmsPage.pageType;
    
    @Column(name = "visible", columnDefinition = "bool")
    @NotNull
    private boolean CmsPage.visible;
    
    @Column(name = "navigable", columnDefinition = "bool")
    @NotNull
    private boolean CmsPage.navigable;
    
    @Column(name = "url", columnDefinition = "varchar", length = 200)
    @NotNull
    private String CmsPage.url;
    
    public Set<CmsPageContent> CmsPage.getCmsPageContents() {
        return cmsPageContents;
    }
    
    public void CmsPage.setCmsPageContents(Set<CmsPageContent> cmsPageContents) {
        this.cmsPageContents = cmsPageContents;
    }
    
    public CmsLayout CmsPage.getLayout() {
        return layout;
    }
    
    public void CmsPage.setLayout(CmsLayout layout) {
        this.layout = layout;
    }
    
    public User CmsPage.getOwner() {
        return owner;
    }
    
    public void CmsPage.setOwner(User owner) {
        this.owner = owner;
    }
    
    public String CmsPage.getName() {
        return name;
    }
    
    public void CmsPage.setName(String name) {
        this.name = name;
    }
    
    public Integer CmsPage.getPageType() {
        return pageType;
    }
    
    public void CmsPage.setPageType(Integer pageType) {
        this.pageType = pageType;
    }
    
    public boolean CmsPage.isVisible() {
        return visible;
    }
    
    public void CmsPage.setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public boolean CmsPage.isNavigable() {
        return navigable;
    }
    
    public void CmsPage.setNavigable(boolean navigable) {
        this.navigable = navigable;
    }
    
    public String CmsPage.getUrl() {
        return url;
    }
    
    public void CmsPage.setUrl(String url) {
        this.url = url;
    }
    
}