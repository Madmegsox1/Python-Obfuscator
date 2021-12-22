package org.madmeg.impl.config;

import org.madmeg.api.FileLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * <p>Loads the config into the constructor {@link Config}</p>
 *
 * @see org.madmeg.api.FileLoader
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class ConfigLoader implements FileLoader {

    public Config config;

    /**
     * @param file The config file being loaded into the {@link Config} constructor
     */
    public ConfigLoader(File file){
        load(file);
    }

    /**
     * @param file The file being loaded by the manager
     */
    @Override
    public void load(File file) {

        config = new Config();

        try {
            Scanner sc = new Scanner(file);
            int i = 1;
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.isBlank() || line.startsWith("#"))continue;
                String[] data = line.replace(" ", "").split("=");
                if(data.length < 2){
                    System.err.println("Incorrect Config at line " + i);
                    System.exit(-1);
                }
                i++;

                switch (data[0]){
                    case "rename_vars" -> config.setVarNames(isTrue(data[1]));
                    case "rename_functions" -> config.setDefNames(isTrue(data[1]));
                    case "rename_class" -> config.setClassNames(isTrue(data[1]));
                    case "name_length" -> config.setNameLength(Integer.parseInt(data[1]));
                    case "name_prefix" -> config.setNamePrefix(data[1]);
                    case "insert_garbage" -> config.setInsertGarbage(isTrue(data[1]));
                    case "garbage_amount" -> config.setGarbageAmount(Integer.parseInt(data[1]));
                    case "garbage_length" -> config.setGarbageLength(Integer.parseInt(data[1]));
                    case "insert_obfStrings" -> config.setInsertObfStrings(isTrue(data[1]));
                    case "obfString_amount" -> config.setObfStringAmount(Integer.parseInt(data[1]));
                    case "remove_comments" -> config.setRemoveComments(isTrue(data[1]));
                    case "encode_imports" -> config.setEncodeImports(isTrue(data[1]));
                    case "encoded_list_garbage_length" -> config.setEncodedListGarbageLength(Integer.parseInt(data[1]));
                    case "encoder_type" -> config.setEncoderType(data[1]);
                    case "binary_splitter" -> config.setBinarySplitter(data[1]);
                    case "encode_strings" ->  config.setEncodeStrings(isTrue(data[1]));
                    case "map_path" -> config.setMapPath(data[1]);
                }
            }
            sc.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param val the sting value
     * @return true if the string value equals true
     */
    private boolean isTrue(String val){
        return val.toLowerCase().contains("true") || val.toLowerCase().contains("t") || val.toLowerCase().contains("1");
    }

    /**
     * @param path The path that the manager will save the file if needed
     */
    @Override
    public void save(String path) {

    }
}
