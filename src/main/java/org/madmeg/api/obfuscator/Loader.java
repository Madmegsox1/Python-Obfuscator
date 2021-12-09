package org.madmeg.api.obfuscator;

import org.madmeg.api.FileLoader;
import org.madmeg.impl.Core;

import java.io.File;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class Loader implements FileLoader {

    public Loader(File file){
        if(!file.exists()){
            Core.LOGGER.printError("The file " + file.getName() + " doesnt exist");
            System.exit(-1);
        }
        Core.LOGGER.printSuccess("Fount file");
        load(file);
    }

    /**
     * @param file The file being loaded by the manager
     */
    @Override
    public void load(File file) {

    }

    /**
     * @param path The path that the manager will save the file if needed
     */
    @Override
    public void save(String path) {

    }
}
