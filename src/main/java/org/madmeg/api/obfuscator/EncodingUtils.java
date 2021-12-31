package org.madmeg.api.obfuscator;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Madmegsox1
 * @since 24/12/2021
 */

public final class EncodingUtils {
    public static String stringToHex(String toConvert){
        return String.format("%040x", new BigInteger(1, toConvert.getBytes(StandardCharsets.UTF_8)));
    }

    public static String largeStringToHex(String toConvert){
        final StringBuilder sb = new StringBuilder();
        char[] ch = toConvert.toCharArray();
        for (char c : ch) {
            String hexString = Integer.toHexString(c);
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static String stringToBase64(String toConvert){
        try {
            return Base64.getEncoder()
                    .encodeToString(toConvert.getBytes(StandardCharsets.UTF_8.toString()));
        } catch(UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String stringToBinary(String input) {
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

    public static String prettyBinary(String binary, int blockSize, String separator) {

        List<String> result = new ArrayList<>();
        int index = 0;
        while (index < binary.length()) {
            result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
            index += blockSize;
        }

        return String.join(separator, result);
    }
}
