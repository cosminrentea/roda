// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.StudyOrgAssoc;

privileged aspect StudyOrgAssoc_Roo_Json {
    
    public String StudyOrgAssoc.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static StudyOrgAssoc StudyOrgAssoc.fromJsonToStudyOrgAssoc(String json) {
        return new JSONDeserializer<StudyOrgAssoc>().use(null, StudyOrgAssoc.class).deserialize(json);
    }
    
    public static String StudyOrgAssoc.toJsonArray(Collection<StudyOrgAssoc> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<StudyOrgAssoc> StudyOrgAssoc.fromJsonArrayToStudyOrgAssocs(String json) {
        return new JSONDeserializer<List<StudyOrgAssoc>>().use(null, ArrayList.class).use("values", StudyOrgAssoc.class).deserialize(json);
    }
    
}