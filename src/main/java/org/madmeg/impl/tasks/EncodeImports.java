package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.EncodingUtils;
import org.madmeg.api.obfuscator.RandomUtils;
import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public final class EncodeImports implements Task {

    private final ArrayList<String> lines;

    public EncodeImports(SplitFile file){
        this.lines = file.lines;
    }
    /**
     * <h2>How it works</h2>
     * <p>
     *     iterates through each line and looks for a <b>import</b> or <b>from</b> it then encodes into either binary,
     *     hex or base64. Then a list will be generated and inserted after that the obfuscator will insert
     *     {@code exec(listName[index].decode())} for every import
     * </p>
     */
    @Override
    public void completeTask() {
        final ArrayList<String> toEncode = new ArrayList<>();
        final ArrayList<Integer> indexes = new ArrayList<>();
        int index = 0;
        for(String line : lines){
            if(!line.startsWith("import") && !line.startsWith("from")){
                index++;
                continue;
            }
            toEncode.add(line);
            indexes.add(index);
            index++;
        }

        final ArrayList<String> encoded = new ArrayList<>();

        for(String unEncoded : toEncode){
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> encoded.add(EncodingUtils.stringToHex(unEncoded).replaceFirst("^0*", ""));
                case "base64" -> encoded.add(EncodingUtils.stringToBase64(unEncoded));
                case "bin" -> encoded.add(EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(unEncoded), 8, Core.CONFIG.getBinarySplitter()));
            }
        }
        final String listName = Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        final StringBuilder list = new StringBuilder(listName + " = [");
        for(int i = 0; i < Core.CONFIG.getEncodedListGarbageLength() - encoded.size(); i++){
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> list.append('"').append(EncodingUtils.stringToHex(RandomUtils.genRandomString(100))).append('"');
                case "base64" -> list.append('"').append(EncodingUtils.stringToBase64(RandomUtils.genRandomString(100))).append('"');
                case "bin" -> list.append('"').append(EncodingUtils.prettyBinary(EncodingUtils.stringToBinary(RandomUtils.genRandomString(100)), 8, Core.CONFIG.getBinarySplitter())).append('"');
            }
            list.append(", ");
        }
        int l = 0;
        for(String encodedS : encoded){
            list.append('"').append(encodedS).append('"');
            if(l < encoded.size() - 1) {
                list.append(", ");
            }
            l++;
        }
        list.append("]");
        final StringBuilder constructedInjectionString = new StringBuilder("\n" + list + "\n");
        for(int i : indexes){
            lines.remove(i);
        }

        if(Core.CONFIG.getEncoderType().equals("base64")){
            constructedInjectionString.append("import base64\n"); // TODO hide in a list
        }

        for(int i = 0; i < indexes.size(); i++){
            int indexL = (Core.CONFIG.getEncodedListGarbageLength()-1) - i;
            constructedInjectionString.append("exec(");
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> constructedInjectionString.append("bytes.fromhex(").append(listName).append("[").append("0x000").append(String.format("%X", indexL)).append("].replace('', '')).decode('utf-8'))\n");
                case "base64" -> constructedInjectionString.append("base64.b64decode(").append(listName).append("[").append("0x000").append(String.format("%X", indexL)).append("]))\n");
                case "bin" -> constructedInjectionString.append("''.join(chr(int(").append(listName).append("[").append("0x000").append(String.format("%X", indexL)).append("].replace('")
                        .append(Core.CONFIG.getBinarySplitter()).append("', '')[i*0x0008:i*0x0008+0x0008],(0x0003 - 0x0001))) for i in range(len(")
                        .append(listName).append("[").append("0x000").append(String.format("%X", indexL)).append("].replace('")
                        .append(Core.CONFIG.getBinarySplitter()).append("', ''))//(0x0004 + 0x0004))))\n");
            }
        }
        lines.add(0, constructedInjectionString.toString());


        index = 0;
        final ArrayList<Integer> toRemove = new ArrayList<>();
        for(String line : lines){
            if(!line.startsWith("import") && !line.startsWith("from")){
                index++;
                continue;
            }
            toRemove.add(index);
        }

        for (int i : toRemove){ // have to have this for some reason >????
            lines.remove(i);
        }
    }
}
