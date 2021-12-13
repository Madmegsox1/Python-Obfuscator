package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RenameClass implements Task {

    private SplitFile file;
    private ArrayList<String> lines;
    public RenameClass(SplitFile file){
        this.file = file;
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        findRef(findFunctions());
    }

    private ArrayList<RenameObject> findFunctions(){
        final Pattern pattern = Pattern.compile("^class\s[a-zA-Z0-9]*");
        final Pattern wPattern = Pattern.compile("^\s +");
        ArrayList<RenameObject> renamesLines = new ArrayList<>();
        for (String line : lines){
            final String tempLine = line.replaceAll("^\s +", "");

            final Matcher matcher = pattern.matcher(tempLine);
            final Matcher wMatcher = wPattern.matcher(line);

            if(!matcher.find())continue;
            final String oldName = tempLine.substring(5).split("\\(")[0].replaceAll(" ", "");
            final RenameObject rename = new RenameObject(oldName, RandomUtils.genRandomString(Core.CONFIG.getNameLength()), (wMatcher.find()) ? wMatcher.group() : "");
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
                line = line.split("[(]")[0];
                line = line.replaceAll(name.getOldName(), name.getNewName() + "()");
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
