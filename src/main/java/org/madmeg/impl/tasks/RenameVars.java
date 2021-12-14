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

public final class RenameVars implements Task {

    private SplitFile file;
    private ArrayList<String> lines;
    public RenameVars(SplitFile file){
        this.file = file;
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        findRef(findVars());
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

    private void findRef(Collection<RenameObject> renameObjects){
        final Map<Integer, String> map = new HashMap<>();
        for(RenameObject name : renameObjects){
            final Pattern patten = Pattern.compile(name.getOldName() + "[=]");
            final Pattern pattern2 = Pattern.compile(name.getNewName() + "[.]");

            int i =0;

            for(String line : lines){
                final String tempLine = line.replaceAll("\s", "").replace(" ", "");
                final Matcher matcher = patten.matcher(tempLine);
                final Matcher matcher2 = pattern2.matcher(tempLine);
                if(!matcher.find() && !matcher2.find()){
                    i++;
                    continue;
                }

                System.out.println(line + " " + line.split("[=]").length);

                map.put(i, line);
                i++;
            }
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
