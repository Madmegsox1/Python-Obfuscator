package org.madmeg.api.obfuscator.tasks;

import java.util.LinkedList;
import java.util.Queue;

public abstract class FactoryWorker<T> {

    public final Queue<T> tasks;

    public FactoryWorker(){
        this.tasks = new LinkedList<>();
    }

    public void queueTask(T task){
        this.tasks.add(task);
    }

    public void clearTasks(){
        this.tasks.clear();
    }

    public void runTasks(){

    }


    public void poolTasks(){

    }

}
