package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;

import java.util.*;

/**
 * @author Madmegsox1
 * @since 31/12/2021
 */

public final class EncodeInts implements Task {

    private final ArrayList<String> lines;

    public EncodeInts(SplitFile file){
        this.lines = file.lines;
    }
    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for ints, The obfuscator then encodes the ints into hex, it will then replace
     *     the string with the encoded string
     * </p>
     */
    @Override
    public void completeTask() {
        Map<Integer, String> renameMap = new HashMap<>();
        int i = 0;
        for(String line: lines){
            final String toEncode = line.replaceAll("[^0-9]+", " ");
            for(final String x : toEncode.trim().split(" ")){
                if(x.equals(""))continue;
                line = line.replaceFirst(x, "0x000" + String.format("%X", Integer.parseInt(x)));
            }
            renameMap.put(i, line);
            i++;
        }

        for(int index : renameMap.keySet()){
            lines.remove(index);
            lines.add(index, renameMap.get(index));
        }
    }
}
