package dev.ggok.jackson.whitelist.pojo;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestPojoWithPolymorphicField<T> {

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    private T polymorphicField;

    // To test default types
    private Map<String, Object> map = Map.of("f1", 5, "f2", 5L);
    private Set<String> set = Set.of("f1", "f2");
    private List<String> list = List.of("f1", "f2");
    private Collection<String> collection = List.of("f1", "f2");

    public T getPolymorphicField() {
        return polymorphicField;
    }

    public void setPolymorphicField(T polymorphicField) {
        this.polymorphicField = polymorphicField;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public void setCollection(Collection<String> collection) {
        this.collection = collection;
    }
}
