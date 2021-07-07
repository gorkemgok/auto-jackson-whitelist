package dev.ggok.jackson.whitelist;

import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

public final class AutoJacksonList {

    public static final String DEFAULT_AUTO_WHITELIST_FILE = "auto.jackson.whitelist";

    public static final String DEFAULT_AUTO_BLACKLIST_FILE = "auto.jackson.blacklist";

    private AutoJacksonList() {
        throw new UnsupportedOperationException("No need to instantiate AutoWhiteList.Use `AutoJacksonList.createValidator()`");
    }

    public static PolymorphicTypeValidator createValidator() {
        FileListLoader whiteListLoader = new FileListLoader(DEFAULT_AUTO_WHITELIST_FILE);
        FileListLoader blackListLoader = new FileListLoader(DEFAULT_AUTO_BLACKLIST_FILE);
        DefaultListRepository listRepository = new DefaultListRepository(whiteListLoader, blackListLoader);
        return new AutoWhiteListPolymorphicTypeValidator(listRepository);
    }

}
