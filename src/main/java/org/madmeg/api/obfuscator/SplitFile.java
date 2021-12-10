package org.madmeg.api.obfuscator;

import java.util.ArrayList;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class SplitFile {
    public ArrayList<String> lines;
    public SplitFile(ArrayList<String> lines){
        this.lines = new ArrayList<>();
        this.lines.addAll(lines);
    }
}
