// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.UserAuthLog;

privileged aspect UserAuthLog_Roo_Json {
    
    public String UserAuthLog.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static UserAuthLog UserAuthLog.fromJsonToUserAuthLog(String json) {
        return new JSONDeserializer<UserAuthLog>().use(null, UserAuthLog.class).deserialize(json);
    }
    
    public static String UserAuthLog.toJsonArray(Collection<UserAuthLog> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<UserAuthLog> UserAuthLog.fromJsonArrayToUserAuthLogs(String json) {
        return new JSONDeserializer<List<UserAuthLog>>().use(null, ArrayList.class).use("values", UserAuthLog.class).deserialize(json);
    }
    
}