// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import ro.roda.domain.GroupMembers;

privileged aspect GroupMembers_Roo_Json {
    
    public String GroupMembers.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static GroupMembers GroupMembers.fromJsonToGroupMembers(String json) {
        return new JSONDeserializer<GroupMembers>().use(null, GroupMembers.class).deserialize(json);
    }
    
    public static String GroupMembers.toJsonArray(Collection<GroupMembers> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<GroupMembers> GroupMembers.fromJsonArrayToGroupMemberses(String json) {
        return new JSONDeserializer<List<GroupMembers>>().use(null, ArrayList.class).use("values", GroupMembers.class).deserialize(json);
    }
    
}