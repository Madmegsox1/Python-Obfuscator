package org.madmeg.impl.config;

/**
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
    private boolean insertObfStrings;
    private int obfStringAmount;


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

    public int getObfStringAmount() {
        return obfStringAmount;
    }

    public void setObfStringAmount(int obfStringAmount) {
        this.obfStringAmount = obfStringAmount;
    }
}
