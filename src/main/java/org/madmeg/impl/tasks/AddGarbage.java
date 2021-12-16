package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class AddGarbage implements Task {
    private final ArrayList<String> lines;

    public AddGarbage(SplitFile file) {
        this.lines = file.lines;
    }

    /**
     * <h2>How it works</h2>
     * <p>
     *     Simple way of doing this, it will go through every line until it meets the line it needs to insert garbage,
     *     it will then generate a random function filled with garbage.
     * </p>
     */
    @Override
    public void completeTask() {
        final Pattern pattern = Pattern.compile("^\s +");
        Map<Integer, String> map = new HashMap<>();
        int i = 0;
        int skips = 0;
        for(String line : lines){
            if(skips >= 1){
                skips++;
                i++;
                if(skips == Core.CONFIG.getGarbageAmount() + 1){
                    skips = 0;
                }
                continue;
            }
            else skips = 0;
            String whiteSpace = null;
            final Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                whiteSpace = matcher.group();
            }
            map.put(i, line + "\n" + ((whiteSpace != null) ? whiteSpace : "") + genFunction(((whiteSpace != null) ? whiteSpace : "")));
            i++;
            skips = 1;
        }

        for(int lineIndex : map.keySet()){
            lines.remove(lineIndex);
            lines.add(lineIndex, map.get(lineIndex));
        }
    }

    private String genFunction(String ws) {
        StringBuilder argument = new StringBuilder();

        final String functionName = Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        argument.append("def ").append(functionName).append("():\n");
        String randomRVal =  Core.CONFIG.getNamePrefix()  + RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        int location = RandomUtils.genRandomInt(1, Core.CONFIG.getGarbageLength() - 1);
        for (int i = 0; i < Core.CONFIG.getGarbageLength(); i++) {
            argument.append("\t");
            argument.append(ws);

            if (i == location) {
                argument.append(randomRVal).append(" = ").append(RandomUtils.genRandomDouble(100, 3000));
                argument.append("\n");
                continue;
            }

            int r = RandomUtils.genRandomInt(0, 3);
            if (r == 1) {
                argument.append(genRandomVar());
            } else {
                argument.append(genRandomList());
            }
            argument.append("\n");
        }

        argument.append("\t").append(ws).append("return (").append(randomRVal).append(" / ").append(RandomUtils.genRandomInt(2, 7)).append(") + ").append(RandomUtils.genRandomDouble(0.0, 100.0));

        return argument.toString();
    }

    private String genRandomVar() {
        return Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength()) +
                " = " +
                ((RandomUtils.genRandomInt(0, 3) == 1) ? RandomUtils.genRandomInt(10000, 999999999) : '"' + RandomUtils.genRandomString(200) + '"');
    }

    private String genRandomList() {
        StringBuilder arguments = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            arguments.append((RandomUtils.genRandomInt(0, 3) == 1) ? RandomUtils.genRandomInt(10000, 999999999) : '"' + RandomUtils.genRandomString(100) + '"');
            if (i < 49) {
                arguments.append(", ");
            }
        }
        return Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength()) + " = [" + arguments + "]";
    }

}
