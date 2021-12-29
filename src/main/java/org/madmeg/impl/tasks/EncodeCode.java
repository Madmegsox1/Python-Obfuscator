package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.EncodingUtils;
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
        StringBuilder encoded = new StringBuilder();

        for(String line : lines){
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> {
                    encoded.append(EncodingUtils.stringToHex(line).replaceFirst("^0*", ""));
                }
                case "base64" -> {
                    encoded.append(EncodingUtils.stringToBase64(line));
                }
                case "bin" -> {
                    encoded.append(EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(line), 8, Core.CONFIG.getBinarySplitter()));
                }
            }
        }

        lines.clear();
        lines.add("test = " + '"' + encoded + '"');
        if(Core.CONFIG.getEncoderType().equals("base64")){
            lines.add("import base64");
        }
        lines.add("exec(bytes.fromhex(test))");
    }
}
