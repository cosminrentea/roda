package ro.roda;

import java.util.HashSet;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "cms_file", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
public class CmsFile {

    public void addToFolder(String folderName, String fileName) {
        CmsFolder folder = CmsFolder.findCmsFoldersByNameEquals(folderName).getSingleResult();
        if (folder == null) {
            folder = new CmsFolder();
            folder.setName(folderName);
        }
        if (folder.getCmsFiles() == null) {
            folder.setCmsFiles(new HashSet<CmsFile>());
        }
        folder.getCmsFiles().add(this);
        setCmsFolderId(folder);
        setFilename(fileName);
        setFilesize((long) 0);
        setLabel("label");
    }
}
