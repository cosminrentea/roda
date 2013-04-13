// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.FilePropertyNameValue;

privileged aspect FilePropertyNameValue_Roo_Json {
    
    public String FilePropertyNameValue.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static FilePropertyNameValue FilePropertyNameValue.fromJsonToFilePropertyNameValue(String json) {
        return new JSONDeserializer<FilePropertyNameValue>().use(null, FilePropertyNameValue.class).deserialize(json);
    }
    
    public static String FilePropertyNameValue.toJsonArray(Collection<FilePropertyNameValue> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<FilePropertyNameValue> FilePropertyNameValue.fromJsonArrayToFilePropertyNameValues(String json) {
        return new JSONDeserializer<List<FilePropertyNameValue>>().use(null, ArrayList.class).use("values", FilePropertyNameValue.class).deserialize(json);
    }
    
}