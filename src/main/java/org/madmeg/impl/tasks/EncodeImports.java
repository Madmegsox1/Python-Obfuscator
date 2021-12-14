package org.madmeg.impl.tasks;

import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;
import org.madmeg.impl.Core;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public final class EncodeImports implements Task {

    private final ArrayList<String> lines;

    public EncodeImports(SplitFile file){
        this.lines = file.lines;
    }

    @Override
    public void completeTask() {
        final ArrayList<String> toEncode = new ArrayList<>();
        for(String line : lines){
            if(!line.startsWith("import") && !line.startsWith("from"))continue;
            toEncode.add(line);
        }
        final ArrayList<String> encoded = new ArrayList<>();
        for(String unEncoded : toEncode){
            switch (Core.CONFIG.getEncoderType().toLowerCase()){
                case "hex" -> encoded.add(stringToHex(unEncoded));
                case "base64" -> encoded.add(stringToBase64(unEncoded));
                case "bin" -> encoded.add(prettyBinary(stringToBinary(unEncoded), 8, Core.CONFIG.getBinarySplitter()));
            }
        }

        encoded.spliterator().forEachRemaining(System.out::println);
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
