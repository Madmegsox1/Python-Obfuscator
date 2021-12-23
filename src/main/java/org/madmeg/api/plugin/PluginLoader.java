package org.madmeg.api.plugin;

import org.madmeg.impl.Core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class PluginLoader extends ClassLoader<Plugin> {

    private final File[] pluginsFiles;
    public final ArrayList<Plugin> plugins;

    public PluginLoader(){
        super(Plugin.class);

        Core.LOGGER.printSuccess("Loading Plugins");

        plugins = new ArrayList<>();
        final File pluginDir = new File("plugins/");
        if (!pluginDir.exists()) pluginDir.mkdir();
        pluginsFiles = pluginDir.listFiles((dir, name) -> name.endsWith(".jar"));

        loadClasses();

        Core.LOGGER.printSuccess("Loaded " + plugins.size() + " plugin");
    }

    private void loadClasses(){
        if(pluginsFiles == null || pluginsFiles.length < 1)return;
        try {
            for(final File file : pluginsFiles){
                    findClasses(file);
            }
            load();
            plugins.addAll(getInstances());
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void init(){
        if (!plugins.isEmpty()) {
            plugins.spliterator().forEachRemaining(Plugin::init);
        }
    }

    public void onLoad(){
        if (!plugins.isEmpty()) {
            plugins.spliterator().forEachRemaining(Plugin::onLoad);
        }
    }

    public void onConfig(){
        if (!plugins.isEmpty()) {
            plugins.spliterator().forEachRemaining(Plugin::onConfig);
        }
    }

    public void onPoolTasks(){
        if (!plugins.isEmpty()) {
            plugins.spliterator().forEachRemaining(Plugin::onPoolTasks);
        }
    }

    public void onExecute(){
        if (!plugins.isEmpty()) {
            plugins.spliterator().forEachRemaining(Plugin::onExecute);
        }
    }
}
