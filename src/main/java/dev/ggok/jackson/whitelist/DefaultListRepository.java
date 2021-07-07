package dev.ggok.jackson.whitelist;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListRepository implements ListRepository {

    private final Set<String> whiteList;

    private final Set<String> blackList;

    public DefaultListRepository(ListLoader whiteListLoader, ListLoader blackListLoader) {
        this.whiteList = ConcurrentHashMap.newKeySet();
        whiteList.addAll(whiteListLoader.load());
        this.blackList = blackListLoader.load();
    }

    @Override
    public PolymorphicTypeValidator.Validity getValidity(String className) {
        if (blackList.contains(className)) {
            return PolymorphicTypeValidator.Validity.DENIED;
        }
        if (whiteList.contains(className)) {
            return PolymorphicTypeValidator.Validity.ALLOWED;
        }
        return PolymorphicTypeValidator.Validity.INDETERMINATE;
    }

    @Override
    public void addToWhiteList(String className) {
        whiteList.add(className);
    }
}
