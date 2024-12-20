package org.example.operations;

import org.example.crud.Reader;
import org.example.crud.Saver;
import org.example.objects.DataBase;
import org.example.objects.Table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drop {
    public void drop(Table table, Map<String, Table> db) throws IOException {
        Saver s = new Saver();
        if (new File(DataBase.dbPath).length() == 0) {
            System.out.println("table not stored");
        }
        else {
            Map<String, String> db_list = new Reader().fileread(DataBase.dbPath);
            db_list.remove(table.table_name);
            System.out.println(s.saveFile(DataBase.dbPath, db_list));
        }
        System.out.println(s.deleteFile(table.path, table));
        db.remove(table.table_name);
    }
    public List<List> dropTable(Table table, Map<String, Table> db) throws IOException {
        drop(table, db);
        List out = new ArrayList<>();
        List<List> output = new ArrayList<>();
        out.add("Table was dropped");
        output.add(out);
        return output;
    };
}
