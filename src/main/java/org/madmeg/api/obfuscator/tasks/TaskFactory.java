package org.madmeg.api.obfuscator.tasks;

import org.madmeg.api.obfuscator.Loader;
import org.madmeg.impl.tasks.*;
import org.madmeg.impl.Core;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

/*
 * TODO add config to hide code in hex or bin or oct
 */

public final class TaskFactory {
    private final TaskManager taskManager;

    public TaskFactory(){
        taskManager = new TaskManager();
    }

    public void executeTasks(){
        this.taskManager.runTasks();
        Loader.FILE.lines.spliterator().forEachRemaining(System.out::println);
    }

    public void poolTasks(){

        int size = Loader.FILE.lines.size();

        if(Core.CONFIG.isRemoveComments()){
            taskManager.queueTask(new RemoveComments(Loader.FILE));
        }
        if(Core.CONFIG.isVarNames()){
            taskManager.queueTask(new RenameVars(Loader.FILE));
        }
        if(Core.CONFIG.isDefNames()){
            taskManager.queueTask(new RenameFunction(Loader.FILE));
        }
        if(Core.CONFIG.isClassNames()){
            taskManager.queueTask(new RenameClass(Loader.FILE));
        }
        if(Core.CONFIG.isEncodeImports()){
            taskManager.queueTask(new EncodeImports(Loader.FILE));
        }
        if(Core.CONFIG.isInsertGarbage()){
            taskManager.queueTask(new AddGarbage(Loader.FILE));
        }
        if(Core.CONFIG.isInsertObfStrings()){
            for(int i=0; i < size; i+= 100) {
                // TODO pool obf string task
            }
        }

        // TODO add save obf file
    }

}
