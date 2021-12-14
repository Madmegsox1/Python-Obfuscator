package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.api.obfuscator.tasks.elements.RenameObject;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Madmegsox1
 * @since 12/12/2021
 */

public final class RenameFunction implements Task {
    private final ArrayList<String> lines;
    public RenameFunction(SplitFile file){
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        //findFunctions().spliterator().forEachRemaining(System.out::println);
        findRef(findFunctions());
    }


    private Collection<RenameObject> findFunctions(){
        final Pattern pattern = Pattern.compile("^def\s[a-zA-Z0-9]*");
        final Pattern wPattern = Pattern.compile("^\s +");
        ArrayList<RenameObject> renamesLines = new ArrayList<>();
        for (String line : lines){
            final String tempLine = line.replaceAll("^\s +", "");

            final Matcher matcher = pattern.matcher(tempLine);
            final Matcher wMatcher = wPattern.matcher(line);

            if(!matcher.find())continue;
            final String oldName = tempLine.substring(4).split("\\(")[0].replaceAll(" ", "");
            if(oldName.equals("__init__"))continue;
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
