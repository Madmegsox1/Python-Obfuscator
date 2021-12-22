package org.madmeg.impl.config;

/**
 * <p>A constructor for configs</p>
 *
 * @author Madmegsox1
 * @since 09/12/2021
 */

public final class Config {

    private boolean varNames;
    private boolean defNames;
    private boolean classNames;
    private int nameLength;
    private String namePrefix;
    private boolean insertGarbage;
    private int garbageAmount;
    private int garbageLength;
    private boolean insertObfStrings;
    private int obfStringAmount;
    private boolean removeComments;


    private boolean encodeImports;
    private int encodedListGarbageLength;
    private String encoderType;
    private String binarySplitter;
    private boolean encodeStrings;
    private String mapPath;

    public Config(){}

    /**
     * @return true if the config references obfuscation of var names {@link org.madmeg.impl.tasks.RenameVars}
     */
    public boolean isVarNames() {
        return varNames;
    }


    /**
     * @param varNames the value set by {@link ConfigLoader}
     */
    public void setVarNames(boolean varNames) {
        this.varNames = varNames;
    }

    /**
     * @return true if the config references obfuscation of function names {@link org.madmeg.impl.tasks.RenameFunction}
     */
    public boolean isDefNames() {
        return defNames;
    }

    /**
     * @param defNames the value set by {@link ConfigLoader}
     */
    public void setDefNames(boolean defNames) {
        this.defNames = defNames;
    }

    /**
     * @return true if the config references obfuscation of class names {@link org.madmeg.impl.tasks.RenameClass}
     */
    public boolean isClassNames() {
        return classNames;
    }

    /**
     * @param classNames the value set by {@link ConfigLoader}
     */
    public void setClassNames(boolean classNames) {
        this.classNames = classNames;
    }

    /**
     * @return the length of the obfuscated names {@link org.madmeg.impl.tasks.RenameClass},
     * {@link org.madmeg.impl.tasks.RenameVars}, {@link org.madmeg.impl.tasks.RenameFunction}
     */
    public int getNameLength() {
        return nameLength;
    }

    /**
     * @param nameLength the value set by {@link ConfigLoader}
     */
    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }

    /**
     * @return the prefix at the start of every obfuscated name {@link org.madmeg.impl.tasks.RenameClass},
     * {@link org.madmeg.impl.tasks.RenameVars}, {@link org.madmeg.impl.tasks.RenameFunction}
     */
    public String getNamePrefix() {
        return namePrefix;
    }

    /**
     * @param namePrefix the value set by {@link ConfigLoader}
     */
    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    /**
     * @return if the obfuscator should insert random functions
     */
    public boolean isInsertGarbage() {
        return insertGarbage;
    }


    /**
     * @param insertGarbage the value set by {@link ConfigLoader}
     */
    public void setInsertGarbage(boolean insertGarbage) {
        this.insertGarbage = insertGarbage;
    }

    /**
     * @return the number of lines hte obfuscator should skip before inserting garbage with {@link org.madmeg.impl.tasks.AddGarbage}
     */
    public int getGarbageAmount() {
        return garbageAmount;
    }

    /**
     * @param garbageAmount the value set by {@link ConfigLoader}
     */
    public void setGarbageAmount(int garbageAmount) {
        this.garbageAmount = garbageAmount;
    }

    public boolean isInsertObfStrings() {
        return insertObfStrings;
    }

    public void setInsertObfStrings(boolean insertObfStrings) {
        this.insertObfStrings = insertObfStrings;
    }

    /**
     * @return the number of lines taken up by garbage {@link org.madmeg.impl.tasks.AddGarbage}
     */
    public int getGarbageLength() {
        return garbageLength;
    }

    /**
     * @param garbageLength the value set by {@link ConfigLoader}
     */
    public void setGarbageLength(int garbageLength) {
        this.garbageLength = garbageLength;
    }

    public int getObfStringAmount() {
        return obfStringAmount;
    }

    public void setObfStringAmount(int obfStringAmount) {
        this.obfStringAmount = obfStringAmount;
    }

    /**
     * @return if the obfuscator should remove comments {@link org.madmeg.impl.tasks.RemoveComments}
     */
    public boolean isRemoveComments() {
        return removeComments;
    }
    /**
     * @param removeComments the value set by {@link ConfigLoader}
     */
    public void setRemoveComments(boolean removeComments) {
        this.removeComments = removeComments;
    }

    /**
     * @return if the obfuscator should encode imports {@link org.madmeg.impl.tasks.EncodeImports}
     */
    public boolean isEncodeImports() {
        return encodeImports;
    }

    /**
     * @param encodeImports the value set by {@link ConfigLoader}
     */
    public void setEncodeImports(boolean encodeImports) {
        this.encodeImports = encodeImports;
    }

    /**
     * @return the length of the garbage in the encoded import list {@link org.madmeg.impl.tasks.EncodeImports}
     */
    public int getEncodedListGarbageLength() {
        return encodedListGarbageLength;
    }

    /**
     * @param encodedListGarbageLength the value set by {@link ConfigLoader}
     */
    public void setEncodedListGarbageLength(int encodedListGarbageLength) {
        this.encodedListGarbageLength = encodedListGarbageLength;
    }

    /**
     * @return the type of encoding {@link org.madmeg.impl.tasks.EncodeImports}
     */
    public String getEncoderType() {
        return encoderType;
    }

    /**
     * @param encoderType the value set by {@link ConfigLoader}
     */
    public void setEncoderType(String encoderType) {
        this.encoderType = encoderType;
    }

    /**
     * @return the char used to split blocks of binary in encoding {@link org.madmeg.impl.tasks.EncodeImports}
     */
    public String getBinarySplitter() {
        return binarySplitter;
    }
    /**
     * @param binarySplitter the value set by {@link ConfigLoader}
     */
    public void setBinarySplitter(String binarySplitter) {
        this.binarySplitter = binarySplitter;
    }

    public boolean isEncodeStrings() {
        return encodeStrings;
    }

    public void setEncodeStrings(boolean encodeStrings) {
        this.encodeStrings = encodeStrings;
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setMapPath(String mapPath) {
        this.mapPath = mapPath;
    }
}
