// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.CatalogAclPK;

privileged aspect CatalogAclPK_Roo_Json {
    
    public String CatalogAclPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static CatalogAclPK CatalogAclPK.fromJsonToCatalogAclPK(String json) {
        return new JSONDeserializer<CatalogAclPK>().use(null, CatalogAclPK.class).deserialize(json);
    }
    
    public static String CatalogAclPK.toJsonArray(Collection<CatalogAclPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<CatalogAclPK> CatalogAclPK.fromJsonArrayToCatalogAclPKs(String json) {
        return new JSONDeserializer<List<CatalogAclPK>>().use(null, ArrayList.class).use("values", CatalogAclPK.class).deserialize(json);
    }
    
}