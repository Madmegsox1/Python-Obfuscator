package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.List;

public final class AddGarbage implements Task {
    private List<String> lines;

    public AddGarbage(SplitFile file, int startLine){
        if(startLine + 100 > file.lines.size())return;

        this.lines = file.lines.subList(startLine, startLine + 100);
    }


    @Override
    public void completeTask() {
        if(lines == null)return;
         for(String line : lines){
            boolean whiteSpace = line.startsWith("\s");
         }
    }

    private String genFunction(){
        StringBuilder argument = new StringBuilder();

        final String functionName = RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        argument.append("def ").append(functionName).append("():\n");
        String randomRVal = RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        for (int i = 0; i < RandomUtils.genRandomInt(10, 50); i++) {
            argument.append("\t");

            if(i == 7){
                argument.append(randomRVal).append(" = ").append(RandomUtils.genRandomDouble(100, 3000));
                argument.append("\n");
                continue;
            }

            int r = RandomUtils.genRandomInt(0, 3);
            if(r == 1){
                argument.append(genRandomVar());
            }else {
                argument.append(genRandomList());
            }
            argument.append("\n");
        }

        argument.append("\t").append("return (").append(randomRVal).append(" / ").append(RandomUtils.genRandomInt(2, 7)).append(") + ").append(RandomUtils.genRandomDouble(0.0, 100.0));

        return argument.toString();
    }

    private  String genRandomVar(){
        return RandomUtils.genRandomString(Core.CONFIG.getNameLength()) +
                " = " +
                ((RandomUtils.genRandomInt(0, 3) == 1) ? RandomUtils.genRandomInt(10000, 999999999) : '"' + RandomUtils.genRandomString(200) + '"');
    }

    private  String genRandomList(){
        StringBuilder arguments = new StringBuilder();
        for(int i = 0; i < 50; i++) {
            arguments.append((RandomUtils.genRandomInt(0, 3) == 1) ? RandomUtils.genRandomInt(10000, 999999999) : '"' + RandomUtils.genRandomString(100) + '"');
            if(i < 49) {
                arguments.append(", ");
            }
        }


        return RandomUtils.genRandomString(Core.CONFIG.getNameLength()) +
                " = [" + arguments + "]";

    }

}
