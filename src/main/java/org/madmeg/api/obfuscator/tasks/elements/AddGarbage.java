package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;

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




    private String getRandomVars(){


        return "";
    }



}
