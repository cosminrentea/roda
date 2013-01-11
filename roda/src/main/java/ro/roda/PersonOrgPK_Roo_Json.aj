// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.PersonOrgPK;

privileged aspect PersonOrgPK_Roo_Json {
    
    public String PersonOrgPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static PersonOrgPK PersonOrgPK.fromJsonToPersonOrgPK(String json) {
        return new JSONDeserializer<PersonOrgPK>().use(null, PersonOrgPK.class).deserialize(json);
    }
    
    public static String PersonOrgPK.toJsonArray(Collection<PersonOrgPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<PersonOrgPK> PersonOrgPK.fromJsonArrayToPersonOrgPKs(String json) {
        return new JSONDeserializer<List<PersonOrgPK>>().use(null, ArrayList.class).use("values", PersonOrgPK.class).deserialize(json);
    }
    
}
