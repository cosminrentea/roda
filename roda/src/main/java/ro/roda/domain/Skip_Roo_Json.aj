// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.Skip;

privileged aspect Skip_Roo_Json {
    
    public String Skip.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Skip Skip.fromJsonToSkip(String json) {
        return new JSONDeserializer<Skip>().use(null, Skip.class).deserialize(json);
    }
    
    public static String Skip.toJsonArray(Collection<Skip> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Skip> Skip.fromJsonArrayToSkips(String json) {
        return new JSONDeserializer<List<Skip>>().use(null, ArrayList.class).use("values", Skip.class).deserialize(json);
    }
    
}