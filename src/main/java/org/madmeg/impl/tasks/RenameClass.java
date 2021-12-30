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

public final class RenameClass extends Mapper<RenameObject> implements Task {

    private final ArrayList<String> lines;
    public RenameClass(SplitFile file){
        super("Classes");
        this.lines = file.lines;
    }

    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for {@code ^class\s[a-zA-Z0-9]*} regex the obfuscator then generates
     *     the new name for the class and maps it the {@link RenameObject} constructor it then renames the classes and
     *     class refs.
     * </p>
     */
    @Override
    public void completeTask() {
        addBulkMaps(findFunctions());
        findRef(maps);
        try {
            saveMaps(Core.CONFIG.getMapPath());
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    private Collection<RenameObject> findFunctions(){
        final Pattern pattern = Pattern.compile("^class\s[a-zA-Z0-9]*");
        final Pattern wPattern = Pattern.compile("^\s +");
        ArrayList<RenameObject> renamesLines = new ArrayList<>();
        for (String line : lines){
            final String tempLine = line.replaceAll("^\s +", "");

            final Matcher matcher = pattern.matcher(tempLine);
            final Matcher wMatcher = wPattern.matcher(line);

            if(!matcher.find())continue;
            final String oldName = tempLine.substring(5).split("\\(")[0].replaceAll(" ", "");
            final RenameObject rename = new RenameObject(oldName, Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength()), (wMatcher.find()) ? wMatcher.group() : "");
            renamesLines.add(rename);
        }
        return renamesLines;
    }

    private void findRef(Collection<RenameObject> renameObject){
        final Map<Integer, String> map = new HashMap<>();
        for(RenameObject name : renameObject){
            final Pattern pattern = Pattern.compile(name.getOldName()+"[(]");

            int i =0;

            for (String line : lines){
                final String tempLine = line.replaceAll("\s", "");
                final Matcher matcher = pattern.matcher(tempLine);
                if(!matcher.find()){
                    i++;
                    continue;
                }

                final FindString findString = new FindString(line, false); // shit fix
                boolean removedString = false;
                if(findString.getFoundLine() != null && findString.getFoundLine().get(0).contains(name.getOldName() + "()")){
                    line = line.replace(findString.getFoundLine().get(0), "[+|---|.]");
                    removedString = true;
                }

                String[] oldLine = line.split("[(]");
                line = oldLine[0];
                line = line.replaceAll(name.getOldName(), name.getNewName() + "()" + ((oldLine[1].contains(":")) ?  ":" : ""));

                if(removedString){
                    line = line.replace("[+|---|.]", findString.getFoundLine().get(0));
                }
                map.put(i, line);
                i++;
            }


        }
        for (int lineIndex : map.keySet()){
            lines.remove(lineIndex);
            lines.add(lineIndex, map.get(lineIndex));
        }

    }
}
