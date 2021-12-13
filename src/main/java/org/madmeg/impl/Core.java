package org.madmeg.impl;

import org.madmeg.api.Color;
import org.madmeg.api.logger.Logger;
import org.madmeg.api.obfuscator.Loader;
import org.madmeg.api.obfuscator.tasks.TaskFactory;
import org.madmeg.impl.config.Config;
import org.madmeg.impl.config.ConfigLoader;

import java.io.File;

/**
 * @author 0x0001
 * @author Madmegsox1
 * @since 09/12/2021
 * @version 1.0.0
 *
 *  <h2>
 *      The main Function is ran by the command line.
 *  </h2>
 *  <p>
 *      To run the this function in command line you need <b>java</b> installed
 *      then you will need to run the command {@code java -jar 'THIS JAR NAME'.jar 'DIR TO CONFIG FILE'}.
 *  </p>
 *
 */

public final class Core {

    private static final String TITLE_TEXT= """
             ____  __ __  ______  __ __   ___   ____        ___   ____   _____  __ __  _____   __   ____  ______  ____  ___   ____     \s
            |    \\|  |  ||      ||  |  | /   \\ |    \\      /   \\ |    \\ |     ||  |  |/ ___/  /  ] /    ||      ||    |/   \\ |    \\    \s
            |  o  )  |  ||      ||  |  ||     ||  _  |    |     ||  o  )|   __||  |  (   \\_  /  / |  o  ||      | |  ||     ||  _  |   \s
            |   _/|  ~  ||_|  |_||  _  ||  O  ||  |  |    |  O  ||     ||  |_  |  |  |\\__  |/  /  |     ||_|  |_| |  ||  O  ||  |  |   \s
            |  |  |___, |  |  |  |  |  ||     ||  |  |    |     ||  O  ||   _] |  :  |/  \\ /   \\_ |  _  |  |  |   |  ||     ||  |  |   \s
            |  |  |     |  |  |  |  |  ||     ||  |  |    |     ||     ||  |   |     |\\    \\     ||  |  |  |  |   |  ||     ||  |  |   \s
            |__|  |____/   |__|  |__|__| \\___/ |__|__|     \\___/ |_____||__|    \\__,_| \\___|\\____||__|__|  |__|  |____|\\___/ |__|__|   \s
                                                                                                                                       \s""";

    public static Logger LOGGER = new Logger("MAIN");
    public static ConfigLoader CONFIG_LOADER;
    public static Config CONFIG;
    public static Loader LOADER;
    public static TaskFactory TASK_FACTORY = new TaskFactory();

    public static void main(final String[] args){
        if(args.length < 1){
            System.err.println("No config file found in arguments!");
            System.exit(-1);
        }

        System.out.println(Color.RED + TITLE_TEXT + Color.RESET);
        LOGGER.printSuccess("Loading config");
        CONFIG_LOADER = new ConfigLoader(new File(args[0]));
        CONFIG = CONFIG_LOADER.config;
        LOGGER.printSuccess("Loaded config");
        LOGGER.printCommand("Input the path to the .py file you would like to Obfuscate: ");
        LOADER = new Loader(new File(LOGGER.readLine()));

        LOGGER.printSuccess("Pooling Obfuscation tasks");
        TASK_FACTORY.poolTasks();
        LOGGER.printSuccess("Pooled Obfuscation tasks");

        LOGGER.printSuccess("Executing Obfuscation tasks");
        TASK_FACTORY.executeTasks();
        LOGGER.printSuccess("Completed all Obfuscation tasks");
    }
}
