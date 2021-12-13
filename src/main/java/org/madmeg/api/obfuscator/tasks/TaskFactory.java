package org.madmeg.api.obfuscator.tasks;

import org.madmeg.api.obfuscator.Loader;
import org.madmeg.impl.tasks.RemoveComments;
import org.madmeg.impl.tasks.RenameClass;
import org.madmeg.impl.tasks.RenameFunction;
import org.madmeg.impl.Core;
import org.madmeg.impl.tasks.RenameVars;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

/*
 * TODO fix add garbage concurrent error.
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
        if(Core.CONFIG.isInsertGarbage()){
            for(int i=0; i < size; i+= 50){
                //taskManager.queueTask(new AddGarbage(Loader.FILE, i));
            }
        }
        if(Core.CONFIG.isInsertObfStrings()){
            for(int i=0; i < size; i+= 100) {
                // TODO pool obf string task
            }
        }

        // TODO add save obf file
    }

}
