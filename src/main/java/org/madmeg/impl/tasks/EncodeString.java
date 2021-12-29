package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.EncodingUtils;
import org.madmeg.api.obfuscator.FindString;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Madmegsox1
 * @since 29/12/2021
 *
 * only works with one string per line, easy fix but haven't got round to it yet
 */

public final class EncodeString implements Task {

    private final ArrayList<String> lines;

    public EncodeString(SplitFile file){
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        int i = 0;
        Map<Integer, String> renameMap = new HashMap<>();
        for(String line : lines){
            final FindString findString = new FindString(line, true);
            final FindString replaceString = new FindString(line, false);
            if(Objects.equals(findString.getFoundLine(), "")){
                i++;
                continue;
            }
            String encoded = "";
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> encoded = "(bytes.fromhex(('"  + (EncodingUtils.stringToHex(replaceString.getFoundLine()).replaceFirst("^0*", "")) + "').replace('', ''))).decode('utf-8')";
                case "base64" -> encoded = "base64.b64decode('" + (EncodingUtils.stringToBase64(replaceString.getFoundLine())) + "')";
                case "bin" -> {
                    encoded = "''.join(chr(int(('";
                    encoded += (EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(replaceString.getFoundLine()), 8, Core.CONFIG.getBinarySplitter()));
                    encoded += "').replace('" + Core.CONFIG.getBinarySplitter() + "', '')";
                    encoded += "[i*0x0008:i*0x0008+0x0008],(0x0003 - 0x0001))) for i in range(len(('";
                    encoded += (EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(replaceString.getFoundLine()), 8, Core.CONFIG.getBinarySplitter()));
                    encoded += "').replace('" + Core.CONFIG.getBinarySplitter() + "', ''))";
                    encoded += "//(0x0004 + 0x0004)))";
                }
            }
            renameMap.put(i, line.replace(findString.getFoundLine(), encoded));

            i++;
        }

        for(int index : renameMap.keySet()){
            lines.remove(index);
            lines.add(index, renameMap.get(index));
        }
    }
}
