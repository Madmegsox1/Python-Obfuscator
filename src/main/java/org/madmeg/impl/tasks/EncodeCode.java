package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.EncodingUtils;
import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;

/**
 * @author Madmegsox1
 * @since 24/12/2021
 */

public final class EncodeCode implements Task {

    private final ArrayList<String> lines;

    public EncodeCode(SplitFile file){
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
         final StringBuilder toEncoded = new StringBuilder();

        for(String line : lines){
            toEncoded.append(line).append("\n");
        }
        String encoded = "";
        switch (Core.CONFIG.getEncoderType().toLowerCase()){
            case "hex" -> {
                Core.LOGGER.printError("Cannot encode code with hex. Please edit your config!");
            }
            case "base64" -> {
                encoded = (EncodingUtils.stringToBase64(toEncoded.toString()));
            }
            case "bin" -> {
                encoded = (EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(toEncoded.toString()), 8, Core.CONFIG.getBinarySplitter()));
            }
        }

        lines.clear();
        final String name = RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        lines.add(name + " = " + '"' + encoded + '"');
        if(Core.CONFIG.getEncoderType().equals("base64")){
            lines.add("import base64");
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("exec(");
        switch (Core.CONFIG.getEncoderType().toLowerCase()){
            case "base64" -> sb.append("base64.b64decode(").append(name).append("))\n");
            case "bin" -> sb.append("''.join(chr(int(").append(name).append(".replace('")
                    .append(Core.CONFIG.getBinarySplitter()).append("', '')[i*0x0008:i*0x0008+0x0008],(0x0003 - 0x0001))) for i in range(len(")
                    .append(name).append(".replace('")
                    .append(Core.CONFIG.getBinarySplitter()).append("', ''))//(0x0004 + 0x0004))))\n");
        }
        lines.add(sb.toString());
    }
}
