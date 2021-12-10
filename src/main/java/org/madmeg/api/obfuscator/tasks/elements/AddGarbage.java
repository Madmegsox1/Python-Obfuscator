package org.madmeg.api.obfuscator.tasks.elements;

import org.madmeg.api.obfuscator.SplitFile;
import org.madmeg.api.obfuscator.tasks.Task;

public final class AddGarbage implements Task {
    private SplitFile file;
    private int startLine;

    public AddGarbage(SplitFile file, int startLine){
        this.file = file;
        this.startLine = startLine;
    }


    @Override
    public void completeTask() {

    }
}
