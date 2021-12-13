package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class RemoveComments implements Task {


    private SplitFile file;
    private ArrayList<String> lines;
    public RemoveComments(SplitFile file){
        this.file = file;
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        Map<Integer, String> newLines = new HashMap<>();
        int i = 0;
        for(String line : lines){
            if(!line.contains("#")) {
                i++;
                continue;
            }
            line = line.split("#")[0];
            newLines.put(i, line);
            i++;
        }

        for(int lineIndex : newLines.keySet()){
            lines.remove(lineIndex);
            lines.add(lineIndex, newLines.get(lineIndex));
        }
    }
}
