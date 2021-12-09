package org.madmeg.api;

import java.io.File;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public interface FileLoader {
    /**
     * @param file The file being loaded by the manager
     */
    void load(File file);



    /**
     * @param path The path that the manager will save the file if needed
     */
    void save(String path);
}
