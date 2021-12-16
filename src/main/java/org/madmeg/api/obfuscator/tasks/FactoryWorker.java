package org.madmeg.api.obfuscator.tasks;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @param <T> The datatype being queued up by a factory like {@link TaskFactory}
 */
public abstract class FactoryWorker<T> {

    public final Queue<T> tasks;

    public FactoryWorker(){
        this.tasks = new LinkedList<>();
    }

    /**
     * @param task the task to be added to the back of the queue
     */
    public void queueTask(T task){
        this.tasks.add(task);
    }

    /**
     * <p>
     *     Clears the queue of all tasks
     * </p>
     */
    public void clearTasks(){
        this.tasks.clear();
    }

    /**
     * <p>
     *     Overridable function to complete tasks pooled by the factory
     * </p>
     */
    public void runTasks(){

    }

    /**
     * <p>
     *     Overridable function to pool tasks
     * </p>
     */
    public void poolTasks(){

    }

}
