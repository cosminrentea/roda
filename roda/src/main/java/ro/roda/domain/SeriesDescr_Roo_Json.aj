// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.SeriesDescr;

privileged aspect SeriesDescr_Roo_Json {
    
    public String SeriesDescr.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static SeriesDescr SeriesDescr.fromJsonToSeriesDescr(String json) {
        return new JSONDeserializer<SeriesDescr>().use(null, SeriesDescr.class).deserialize(json);
    }
    
    public static String SeriesDescr.toJsonArray(Collection<SeriesDescr> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<SeriesDescr> SeriesDescr.fromJsonArrayToSeriesDescrs(String json) {
        return new JSONDeserializer<List<SeriesDescr>>().use(null, ArrayList.class).use("values", SeriesDescr.class).deserialize(json);
    }
    
}
