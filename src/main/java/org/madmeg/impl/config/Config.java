package org.madmeg.impl.config;

/**
 * @author Madmegsox1
 * @since 09/12/2021
 *
 * TODO add new configs for garbage
 *
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

    public Config(){}

    public boolean isVarNames() {
        return varNames;
    }

    public void setVarNames(boolean varNames) {
        this.varNames = varNames;
    }

    public boolean isDefNames() {
        return defNames;
    }

    public void setDefNames(boolean defNames) {
        this.defNames = defNames;
    }

    public boolean isClassNames() {
        return classNames;
    }

    public void setClassNames(boolean classNames) {
        this.classNames = classNames;
    }

    public int getNameLength() {
        return nameLength;
    }

    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public boolean isInsertGarbage() {
        return insertGarbage;
    }

    public void setInsertGarbage(boolean insertGarbage) {
        this.insertGarbage = insertGarbage;
    }

    public int getGarbageAmount() {
        return garbageAmount;
    }

    public void setGarbageAmount(int garbageAmount) {
        this.garbageAmount = garbageAmount;
    }

    public boolean isInsertObfStrings() {
        return insertObfStrings;
    }

    public void setInsertObfStrings(boolean insertObfStrings) {
        this.insertObfStrings = insertObfStrings;
    }

    public int getGarbageLength() {
        return garbageLength;
    }

    public void setGarbageLength(int garbageLength) {
        this.garbageLength = garbageLength;
    }

    public int getObfStringAmount() {
        return obfStringAmount;
    }

    public void setObfStringAmount(int obfStringAmount) {
        this.obfStringAmount = obfStringAmount;
    }

    public boolean isRemoveComments() {
        return removeComments;
    }

    public void setRemoveComments(boolean removeComments) {
        this.removeComments = removeComments;
    }

    public boolean isEncodeImports() {
        return encodeImports;
    }

    public void setEncodeImports(boolean encodeImports) {
        this.encodeImports = encodeImports;
    }

    public int getEncodedListGarbageLength() {
        return encodedListGarbageLength;
    }

    public void setEncodedListGarbageLength(int encodedListGarbageLength) {
        this.encodedListGarbageLength = encodedListGarbageLength;
    }

    public String getEncoderType() {
        return encoderType;
    }

    public void setEncoderType(String encoderType) {
        this.encoderType = encoderType;
    }

    public String getBinarySplitter() {
        return binarySplitter;
    }

    public void setBinarySplitter(String binarySplitter) {
        this.binarySplitter = binarySplitter;
    }
}
