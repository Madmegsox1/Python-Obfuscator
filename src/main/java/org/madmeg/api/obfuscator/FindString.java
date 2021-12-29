package org.madmeg.api.obfuscator;

/**
 * @author Madmegsox1
 * @since 29/12/2021
 *
 * bunch of shit code but idk
 */

public final class FindString {
    private final String line;
    private String foundLine;
    private int start;
    private final boolean keepChar;

    public FindString(String line, boolean keepChar){
        this.line = line;
        this.start = -1;
        this.keepChar = keepChar;
        this.foundLine = find();
    }

    private String find(){
        if(!line.contains("\"") && !line.contains("'"))return "";
        boolean open = false;
        boolean open2 = false;

        final StringBuilder toReturn = new StringBuilder();
        for(int i = 0; i < line.length(); i++){
            final char val = line.charAt(i);
            if(val == '"' && !open2){
                open = !open;
                if(!keepChar) {
                    continue;
                }else if(!open){
                    toReturn.append('"');
                }
            }
            if(val == '\'' && !open){
                open2 = !open2;
                if(!keepChar) {
                    continue;
                }else if(!open2){
                    toReturn.append("'");
                }
            }

            if(open || open2){
                if(start == -1){
                    start = i;
                }
                toReturn.append(val);
            }
        }
        return toReturn.toString();
    }

    public String getFoundLine() {
        return foundLine;
    }

    public int getStart() {
        return start;
    }
}
