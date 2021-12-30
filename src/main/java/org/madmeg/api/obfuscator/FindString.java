package org.madmeg.api.obfuscator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Madmegsox1
 * @since 29/12/2021
 *
 * bunch of shit code, need to improve this as its very slow
 */

public final class FindString {
    private final String line;
    private ArrayList<String> foundLine;
    private int start;
    private final boolean keepChar;

    public FindString(String line, boolean keepChar){
        this.line = line;
        this.start = -1;
        this.keepChar = keepChar;
        this.foundLine = find();
    }

    private ArrayList<String> find(){
        if(!line.contains("\"") && !line.contains("'"))return null;
        boolean open = false;
        boolean open2 = false;


        ArrayList<String> strings = new ArrayList<>();


        StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < line.length(); i++){
            final char val = line.charAt(i);
            if(val == '"' && !open2){
                open = !open;
                if(!open){
                    if(keepChar) toReturn.append('"');
                    strings.add(toReturn.toString());
                    toReturn = new StringBuilder();
                }
                if(!keepChar) {
                    continue;
                }
            }
            if(val == '\'' && !open){
                open2 = !open2;
                if(!open2){
                    if(keepChar) toReturn.append("'");
                    strings.add(toReturn.toString());
                    toReturn = new StringBuilder();
                }
                if(!keepChar) {
                    continue;
                }
            }

            if(open || open2){
                if(start == -1){
                    start = i;
                }
                toReturn.append(val);
            }
        }
        return strings;
    }

    public ArrayList<String> getFoundLine() {
        return foundLine;
    }

    public int getStart() {
        return start;
    }
}
