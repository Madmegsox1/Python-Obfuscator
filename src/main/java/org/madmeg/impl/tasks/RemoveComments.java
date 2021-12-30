package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.FindString;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class RemoveComments implements Task {


    private final ArrayList<String> lines;
    public RemoveComments(SplitFile file){
        this.lines = file.lines;
    }

    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for a <b>#</b> it then splits the line at that point.
     *     After that it will get index 0 of the list as everything after the <b>#</b> will be commented
     *     out. Finally it will replace the line with the split one.
     * </p>
     */
    @Override
    public void completeTask() {
        Map<Integer, String> newLines = new HashMap<>();
        int i = 0;
        for(String line : lines){
            if(!line.contains("#")) {
                i++;
                continue;
            }
            final FindString findString = new FindString(line, false); // shit fix
            boolean removedString = false;
            if(findString.getFoundLine() != null && findString.getFoundLine().get(0).contains("#")){
                line = line.replace(findString.getFoundLine().get(0), "[+|---|.]");
                removedString = true;
            }

            line = line.split("#")[0];
            if(removedString){
                line = line.replace("[+|---|.]", findString.getFoundLine().get(0));
            }
            newLines.put(i, line);
            i++;
        }

        for(int lineIndex : newLines.keySet()){
            lines.remove(lineIndex);
            lines.add(lineIndex, newLines.get(lineIndex));
        }
    }
}
