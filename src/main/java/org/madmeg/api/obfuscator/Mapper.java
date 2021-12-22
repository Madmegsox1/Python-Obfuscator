package org.madmeg.api.obfuscator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Mapper<T> {
    public final ArrayList<T> maps;
    public final String mappingType;

    public Mapper(final String mappingType){
        maps = new ArrayList<>();
        this.mappingType = mappingType;
    }

    public final void addMap(final T map){
        maps.add(map);
    }

    public final void addBulkMaps(final Collection<T> bulkMaps){
        maps.addAll(bulkMaps);
    }

    public final void saveMaps(final String path) throws IllegalAccessException, IOException {
        if(maps.isEmpty())return;

        final Field[] fields = maps.get(0).getClass().getFields();
        final int[] index = new int[2];
        int i = 0;
        for(Field field : fields){
            final String fName = field.getName().toLowerCase();

            if(fName.equals("oldname")){
                index[0] = i;
            }else if(fName.equals("newname")){
                index[1] = i;
            }
            i++;
        }

        final Field oldNameF = fields[index[0]];
        oldNameF.setAccessible(true);
        final Field newNameF = fields[index[1]];
        newNameF.setAccessible(true);
        if(!new File(path).exists()){
            System.err.println("Cannot create map, the path " + path + " is invalid.");
            return;
        }
        final File mappingFile = new File(path + "/" + mappingType + ".csv");
        mappingFile.createNewFile();

        final FileWriter writer = new FileWriter(mappingFile);

        for(final T map : maps){
            writer.write((String) oldNameF.get(map));
            writer.write(",");
            writer.write((String) newNameF.get(map));
            writer.write("\n");
        }

        writer.close();
    }
}
