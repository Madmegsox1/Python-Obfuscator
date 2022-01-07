package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.Mapper;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.api.obfuscator.tasks.elements.RenameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameImports extends Mapper<RenameObject> implements Task {
    private final ArrayList<String> lines;

    public RenameImports(SplitFile file) {
        super("Imports");
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {


    }


    private Collection<RenameObject> findImports(){
        final ArrayList<RenameObject> objects = new ArrayList<>();
        final Pattern pattern = Pattern.compile("^import\s.*");

        for(String line : lines){
            final Matcher matcher = pattern.matcher(line);
            if(!matcher.find())continue;

            final String oldName = line.substring(6).split(" ")[0];

        }

        return objects;
    }
}
