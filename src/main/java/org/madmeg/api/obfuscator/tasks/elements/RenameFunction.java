package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Madmegsox1
 * @since 12/12/2021
 */

public final class RenameFunction implements Task {
    private SplitFile file;
    private ArrayList<String> lines;
    public RenameFunction(SplitFile file){
        this.file = file;
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        findFunctions().spliterator().forEachRemaining(System.out::println);
    }


    private ArrayList<RenameObject> findFunctions(){
        final Pattern pattern = Pattern.compile("^def\s[a-zA-Z0-9]*");
        final Pattern wPattern = Pattern.compile("^\s +");
        ArrayList<RenameObject> renamesLines = new ArrayList<>();
        for (String line : lines){
            final String tempLine = line.replaceAll("^\s +", "");

            final Matcher matcher = pattern.matcher(tempLine);
            final Matcher wMatcher = wPattern.matcher(line);

            if(!matcher.find())continue;
            final  String oldName = tempLine.substring(4).split("\\(")[0].replaceAll(" ", "");
            final RenameObject rename = new RenameObject(oldName, RandomUtils.genRandomString(Core.CONFIG.getNameLength()), (wMatcher.find()) ? wMatcher.group() : "");
            renamesLines.add(rename);
        }
        return renamesLines;
    }
}
