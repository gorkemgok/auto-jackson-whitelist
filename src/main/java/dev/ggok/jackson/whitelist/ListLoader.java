package dev.ggok.jackson.whitelist;

import java.util.Set;

public interface ListLoader {

    /**
     * Loads list of class names
     *
     * @return Immutable Set of class names
     */
    Set<String> load();

}
