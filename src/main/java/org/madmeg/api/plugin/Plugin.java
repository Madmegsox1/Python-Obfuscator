package org.madmeg.api.plugin;

public interface Plugin {

    void init();

    void onLoad();

    void onConfig();

    void onPoolTasks();

    void onExecute();

}
