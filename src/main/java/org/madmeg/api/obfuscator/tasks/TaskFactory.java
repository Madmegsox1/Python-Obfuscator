package org.madmeg.api.obfuscator.tasks;

import org.madmeg.api.obfuscator.Loader;
import org.madmeg.impl.tasks.*;
import org.madmeg.impl.Core;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class TaskFactory extends FactoryWorker<Task>{

    @Override
    public void runTasks(){
        while (tasks.size() > 0){
            tasks.remove().completeTask();
        }
        //Loader.FILE.lines.spliterator().forEachRemaining(System.out::println);
    }

    @Override
    public void poolTasks(){
        if(Core.CONFIG.isRemoveComments()){
            this.queueTask(new RemoveComments(Loader.FILE));
        }
        if(Core.CONFIG.isEncodeInts()){
            this.queueTask(new EncodeInts(Loader.FILE));
        }
        if(Core.CONFIG.isEncodeStrings()){
            this.queueTask(new EncodeString(Loader.FILE));
        }
        if(Core.CONFIG.isEncodeImports()){
            this.queueTask(new EncodeImports(Loader.FILE));
        }
        if(Core.CONFIG.isVarNames()){
            this.queueTask(new RenameVars(Loader.FILE));
        }
        if(Core.CONFIG.isDefNames()){
            this.queueTask(new RenameFunction(Loader.FILE));
        }
        if(Core.CONFIG.isClassNames()){
            this.queueTask(new RenameClass(Loader.FILE));
        }
        if(Core.CONFIG.isInsertGarbage()){
            this.queueTask(new AddGarbage(Loader.FILE));
        }
        if(Core.CONFIG.isEncodeCode()){
            this.queueTask(new EncodeCode(Loader.FILE));
        }

    }

}
