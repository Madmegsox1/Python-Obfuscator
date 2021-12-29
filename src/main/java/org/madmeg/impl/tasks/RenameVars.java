package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.FindString;
import org.madmeg.api.obfuscator.Mapper;
import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.api.obfuscator.tasks.elements.RenameObject;
import org.madmeg.impl.Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
 there are a few issues with this:
    1. It will replace names that are of strings value not vars
    2. It won't replace vars that are like {conn, addr = s.accept()} or if there is a ',' in the line
 */


public final class RenameVars extends Mapper<RenameObject> implements Task {

    private final ArrayList<String> lines;
    public RenameVars(SplitFile file){
        super("Vars");
        this.lines = file.lines;
    }
    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for {@code [a-zA-Z0-9]* =[^=]} regex the obfuscator then generates
     *     the new name for the class and maps it the {@link RenameObject} constructor it then renames the var and
     *     var refs.
     * </p>
     */
    @Override
    public void completeTask() {
        addBulkMaps(findVars());
        findRef(maps);
        try {
            saveMaps(Core.CONFIG.getMapPath());
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    private Collection<RenameObject> findVars(){
        final Pattern pattern = Pattern.compile("[a-zA-Z0-9]* =[^=]");
        final Pattern wPattern = Pattern.compile("^\s +");
        ArrayList<RenameObject> renamedLines = new ArrayList<>();
        for (String line : lines){
            final Matcher matcher = pattern.matcher(line);
            final Matcher wMatcher = wPattern.matcher(line);
            if(!matcher.find() || line.contains(","))continue;
            String oldName = line.split("=")[0].replace("\s", "");
            if(contains(renamedLines, oldName))continue;
            String newName = Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength());
            renamedLines.add(new RenameObject(oldName, newName, (wMatcher.find()) ? wMatcher.group() : ""));
        }
        return renamedLines;
    }

    private void findRef(Collection<RenameObject> renameObjects) {
        final Map<Integer, String> map = new HashMap<>();
        int i = 0;
        for (String line : lines) {
            for (RenameObject name : renameObjects) {
                if (!line.contains(name.getOldName())) continue;

                final FindString findString = new FindString(line, false); // shit fix
                boolean removedString = false;
                if(findString.getFoundLine().contains(name.getOldName())){
                    line = line.replace(findString.getFoundLine(), "[+|---|.]");
                    removedString = true;
                }

                line = line.replace(name.getOldName(), name.getNewName());

                if(removedString){
                    line = line.replace("[+|---|.]", findString.getFoundLine());
                }
                map.put(i, line);
            }
            i++;
        }


        for (int lineIndex : map.keySet()){
            lines.remove(lineIndex);
            lines.add(lineIndex, map.get(lineIndex));
        }
    }

    private boolean contains(ArrayList<RenameObject> renamed, String line){
        for(RenameObject object : renamed){
            if(object.getOldName().equals(line))return true;
        }
        return false;
    }
}
