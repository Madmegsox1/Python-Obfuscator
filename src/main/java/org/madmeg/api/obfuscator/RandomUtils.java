package org.madmeg.api.obfuscator;

public final class RandomUtils {

    public static String genRandomString(int size){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    public static int genRandomInt(int min, int max){
        return (int) (Math.random() * max + min);
    }

    public static double genRandomDouble(double min, double max){
        return (Math.random() * max + min);
    }

}
