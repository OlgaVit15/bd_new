package org.example.objects;

import org.example.crud.Reader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DataBase {
    public Map <String, Table> db;
    public static String dbPath = "db_meta.txt";

    public Map<String, Table> init(String path) throws IOException {
        Map<String, Table> db = new HashMap<>();
        if (new File(path).length() == 0){
            db = new HashMap<>();
        }
        else {
            Map<String, String> db_list = new Reader().fileread(path);
            for (Map.Entry<String, String> ent : db_list.entrySet()) {
                Table table = new Reader().tableread(ent.getValue());
                db.put(table.table_name, table);
            }
        }
        return db;
    }
}
