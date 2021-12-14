package org.madmeg.impl.tasks;

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
                case "hex" -> encoded.add(stringToHex(unEncoded));
                case "base64" -> encoded.add(stringToBase64(unEncoded));
                case "bin" -> encoded.add(prettyBinary(stringToBinary(unEncoded), 8, Core.CONFIG.getBinarySplitter()));
            }
        }
        final String listName = Core.CONFIG.getNamePrefix() + RandomUtils.genRandomString(Core.CONFIG.getNameLength());
        final StringBuilder list = new StringBuilder(listName + " = [");
        for(int i = 0; i < Core.CONFIG.getEncodedListGarbageLength() - encoded.size(); i++){
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> list.append('"').append(stringToHex(RandomUtils.genRandomString(100))).append('"');
                case "base64" -> list.append('"').append(stringToBase64(RandomUtils.genRandomString(100))).append('"');
                case "bin" -> list.append('"').append(prettyBinary(stringToBinary(RandomUtils.genRandomString(100)), 8, Core.CONFIG.getBinarySplitter())).append('"');
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
        lines.remove(0);
        for(int i = 0; i < indexes.size(); i++){
            constructedInjectionString.append("exec(").append(listName).append("[").append((Core.CONFIG.getEncodedListGarbageLength()-1) - i).append("])\n");
        }
        lines.add(0, constructedInjectionString.toString());
    }

    private String stringToHex(String toConvert){
        return String.format("%040x", new BigInteger(1, toConvert.getBytes(StandardCharsets.UTF_8)));
    }

    private String stringToBase64(String toConvert){
        try {
            return Base64.getEncoder()
                    .encodeToString(toConvert.getBytes(StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String stringToBinary(String input) {
        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))
                            .replaceAll(" ", "0")
            );
        }
        return result.toString();
    }

    private String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return String.join(separator, result);
    }
}