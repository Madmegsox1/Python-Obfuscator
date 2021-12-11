package org.madmeg.api.obfuscator.tasks;

import org.madmeg.impl.Core;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class TaskManager {
    private final Queue<Task> tasks;

    public TaskManager(){
        this.tasks = new LinkedList<>();
    }

    public void runTasks(){
        while (tasks.size() > 0){
            tasks.remove().completeTask();
        }
    }

    public void queueTask(Task task){
        this.tasks.add(task);
    }

    public void clearTasks(){
        this.tasks.clear();
    }

}
