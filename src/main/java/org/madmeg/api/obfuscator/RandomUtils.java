package org.madmeg.api.obfuscator;

/**
 * <p>A static util class used to generate random data values</p>
 */
public final class RandomUtils {
    /**
     * @param size the length of the generated string
     * @return the generated string
     */
    public static String genRandomString(int size){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
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

    /**
     * @param min the smallest int that can be generated
     * @param max the largest int that can be generated
     * @return the randomly generated integer
     */
    public static int genRandomInt(int min, int max){
        return (int) (Math.random() * max + min);
    }

    /**
     * @param min the smallest double that can be generated
     * @param max the largest double that can be generated
     * @return the randomly generated double
     */
    public static double genRandomDouble(double min, double max){
        return (Math.random() * max + min);
    }

}
