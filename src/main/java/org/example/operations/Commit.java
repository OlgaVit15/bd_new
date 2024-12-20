package org.example.operations;

import org.example.crud.Saver;
import org.example.objects.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Commit {
    public List<List> commit(Table table) throws IOException {
        Saver saver = new Saver();
        saver.saveFile(table.path, table);
        List out = new ArrayList();
        List<List> output = new ArrayList<>();
        out.add("commited");
        output.add(out);
        return output;
    }
}
