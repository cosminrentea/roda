// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.AuditFieldPK;

privileged aspect AuditFieldPK_Roo_Json {
    
    public String AuditFieldPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static AuditFieldPK AuditFieldPK.fromJsonToAuditFieldPK(String json) {
        return new JSONDeserializer<AuditFieldPK>().use(null, AuditFieldPK.class).deserialize(json);
    }
    
    public static String AuditFieldPK.toJsonArray(Collection<AuditFieldPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<AuditFieldPK> AuditFieldPK.fromJsonArrayToAuditFieldPKs(String json) {
        return new JSONDeserializer<List<AuditFieldPK>>().use(null, ArrayList.class).use("values", AuditFieldPK.class).deserialize(json);
    }
    
}
