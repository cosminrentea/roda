// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.InstancePerson;

privileged aspect InstancePerson_Roo_Json {
    
    public String InstancePerson.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static InstancePerson InstancePerson.fromJsonToInstancePerson(String json) {
        return new JSONDeserializer<InstancePerson>().use(null, InstancePerson.class).deserialize(json);
    }
    
    public static String InstancePerson.toJsonArray(Collection<InstancePerson> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<InstancePerson> InstancePerson.fromJsonArrayToInstancepeople(String json) {
        return new JSONDeserializer<List<InstancePerson>>().use(null, ArrayList.class).use("values", InstancePerson.class).deserialize(json);
    }
    
}