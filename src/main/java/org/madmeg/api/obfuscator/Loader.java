package org.madmeg.api.obfuscator;

import org.madmeg.api.FileLoader;
import org.madmeg.impl.Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class Loader implements FileLoader {

    public static SplitFile FILE;

    public Loader(File file){
        if(!file.exists()){
            Core.LOGGER.printError("The file " + file.getName() + " doesnt exist");
            System.exit(-1);
        }
        Core.LOGGER.printSuccess("Fount file");
        load(file);
        Core.LOGGER.printSuccess("Loaded file");
    }

    /**
     * @param file The file being loaded by the manager
     */
    @Override
    public void load(File file) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.startsWith("\n")){
                    line = line.replace("\n", "");
                }
                lines.add(line);
            }
            FILE = new SplitFile(lines);
            lines.clear();
        }catch (final FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * @param path The path that the manager will save the file if needed
     */
    @Override
    public void save(String path) {

    }
}
