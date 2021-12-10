package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.List;

public final class AddGarbage implements Task {
    private SplitFile file;
    private List<String> lines;
    private int startLine;

    public AddGarbage(SplitFile file, int startLine){
        this.file = file;
        if(startLine + 100 > file.lines.size())return;

        this.lines = file.lines.subList(startLine, startLine + 100);
        this.startLine = startLine;
    }


    @Override
    public void completeTask() {
        if(lines == null)return;
         for(String line : lines){


         }
    }




    public static String genRandomVar(){
        return RandomUtils.genRandomString(Core.CONFIG.getNameLength()) +
                " = " +
                ((RandomUtils.genRandomInt(0, 3) == 1) ? RandomUtils.genRandomInt(10000, 999999999) : '"' + RandomUtils.genRandomString(200) + '"');
    }



}
