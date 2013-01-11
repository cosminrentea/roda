// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.InstanceAclPK;

privileged aspect InstanceAclPK_Roo_Json {
    
    public String InstanceAclPK.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static InstanceAclPK InstanceAclPK.fromJsonToInstanceAclPK(String json) {
        return new JSONDeserializer<InstanceAclPK>().use(null, InstanceAclPK.class).deserialize(json);
    }
    
    public static String InstanceAclPK.toJsonArray(Collection<InstanceAclPK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<InstanceAclPK> InstanceAclPK.fromJsonArrayToInstanceAclPKs(String json) {
        return new JSONDeserializer<List<InstanceAclPK>>().use(null, ArrayList.class).use("values", InstanceAclPK.class).deserialize(json);
    }
    
}
