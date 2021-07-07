package dev.ggok.jackson.whitelist;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static java.lang.String.format;

public class FileListLoader implements ListLoader {

    private static final Logger log = Logger.getLogger(FileListLoader.class.getName());

    private final String listFile;

    public FileListLoader(String listFile) {
        this.listFile = listFile;
    }

    @Override
    public Set<String> load() {
        try {
            URL url = getClass().getClassLoader().getResource(listFile);
            if (url == null) {
                throw new IOException();
            }
            Path path = Paths.get(url.toURI());
            List<String> classNameList = Files.readAllLines(path);
            return Set.copyOf(classNameList);
        } catch (IOException | URISyntaxException e) {
            log.warning(format("No such file: %s", listFile));
            return Collections.emptySet();
        }
    }
}
