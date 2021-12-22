package org.madmeg.api.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarFile;

public abstract class ClassLoader<T> {
    private final ArrayList<URL> urls;
    private final ArrayList<String> classes;
    private final Class<?> type;
    private final ArrayList<T> instances;


    public ClassLoader(Class<?> type){
        urls = new ArrayList<>();
        classes = new ArrayList<>();
        instances = new ArrayList<>();
        this.type = type;
    }


    public void findClasses(File file) throws IOException {
        final JarFile pluginJar = new JarFile(file);
        pluginJar.stream().spliterator().forEachRemaining(e -> {
            if(e.getName().endsWith(".class")){
                classes.add(e.getName());
            }
        });
        urls.add(file.toURI().toURL());
    }

    public void load() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        final URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
        for(final String className : classes){
            Class cls = classLoader.loadClass(className.replaceAll("/", ".").replace(".class", ""));
            Class[] ife = cls.getInterfaces();
            if(ife[0].equals(type)){
                T instance = (T) cls.newInstance();
                instances.add(instance);
            }
        }
    }

    public Collection<T> getInstances(){
        return this.instances;
    }

}
