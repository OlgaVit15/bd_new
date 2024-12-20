package org.example.operations;

import org.example.objects.DataBase;
import org.example.objects.Table;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDatabase {
    public DataBase createDB(String path) throws IOException {
        //new File("./target" + path).mkdirs();
        new File("db_meta.txt").createNewFile();
        DataBase db = new DataBase();
        db.db = new HashMap<>();
        return db;
    }
}
