package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.EncodingUtils;
import org.madmeg.api.obfuscator.FindString;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for strings, The obfuscator then encodes the string into hex, bin or
     *     base64 depending on the config, it will then replace the string with the encoded string
     * </p>
     */
    @Override
    public void completeTask() {
        int i = 0;
        Map<Integer, String> renameMap = new HashMap<>();
        for(String line : lines){

            final FindString findString = new FindString(line, true);
            final FindString replaceString = new FindString(line, false);

            if(findString.getFoundLine() == null || findString.getFoundLine().isEmpty()){
                i++;
                continue;
            }

            int x = 0;
            for(String s : findString.getFoundLine()){
                String encoded = "";
                switch (Core.CONFIG.getEncoderType().toLowerCase()){
                    case "hex" -> encoded = "(bytes.fromhex(('"  + (EncodingUtils.stringToHex(replaceString.getFoundLine().get(x)).replaceFirst("^0*", "")) + "').replace('', ''))).decode('utf-8')";
                    case "base64" -> encoded = "base64.b64decode('" + (EncodingUtils.stringToBase64(replaceString.getFoundLine().get(x))) + "')";
                    case "bin" -> {
                        encoded = "''.join(chr(int(('";
                        encoded += (EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(replaceString.getFoundLine().get(x)), 8, Core.CONFIG.getBinarySplitter()));
                        encoded += "').replace('" + Core.CONFIG.getBinarySplitter() + "', '')";
                        encoded += "[i*0x0008:i*0x0008+0x0008],(0x0003 - 0x0001))) for i in range(len(('";
                        encoded += (EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(replaceString.getFoundLine().get(x)), 8, Core.CONFIG.getBinarySplitter()));
                        encoded += "').replace('" + Core.CONFIG.getBinarySplitter() + "', ''))";
                        encoded += "//(0x0004 + 0x0004)))";
                    }
                }
                x++;
                line = line.replace(s, encoded);
            }


            renameMap.put(i, line);
            i++;
        }

        for(int index : renameMap.keySet()){
            lines.remove(index);
            lines.add(index, renameMap.get(index));
        }
    }
}
