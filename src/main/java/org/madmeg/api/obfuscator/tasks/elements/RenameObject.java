package org.madmeg.api.obfuscator.tasks.elements;

/**
 * @author Madmegsox1
 * @since 12/12/2021
 */

public final class RenameObject {
    public String oldName;
    public String newName;
    private String wSpace;

    public RenameObject(String oldName, String newName, String wSpace){
        this.oldName = oldName;
        this.newName = newName;
        this.wSpace = wSpace;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getwSpace() {
        return wSpace;
    }

    public void setwSpace(String wSpace) {
        this.wSpace = wSpace;
    }

    @Override
    public String toString(){
        return "Old name: '" + oldName + "' New name: '" + newName + "' wSpace:" + wSpace + ":";
    }


}
