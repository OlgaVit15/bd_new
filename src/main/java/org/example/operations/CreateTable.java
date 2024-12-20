package org.example.operations;

import org.example.crud.Reader;
import org.example.objects.Columns;
import org.example.crud.Saver;
import org.example.iCreateTable;
import org.example.objects.DataBase;
import org.example.objects.Table;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateTable implements iCreateTable {
    public void save(Table table, Map<String, Table> db) throws IOException {
        Saver s = new Saver();
        System.out.println(s.saveFile(table.path, table));
        db.put(table.table_name, table);

        if (new File(DataBase.dbPath).length() == 0) {
            Map<String, String> db_list = new HashMap<>();
            db_list.put(table.table_name, table.path);
            System.out.println(s.saveFile(DataBase.dbPath, db_list));
        }
        else {
            Map<String, String> db_list = new Reader().fileread(DataBase.dbPath);
            db_list.put(table.table_name, table.path);
            System.out.println(s.saveFile(DataBase.dbPath, db_list));
        }
    }

    public List<List> createTable(String name, String location, Map<String, String> columntypes, Map<String, Table> db) throws IOException {
        Table table = new Table();
        table.table_name = name;
        table.path = location;
        Columns columns = new Columns(columntypes);
        table.collumns = columns.columns;
        table.columntypes = columntypes;
        save(table, db);
        List out = new ArrayList<>();
        out.add("Table was created");
        List<List> output = new ArrayList<>();
        output.add(out);
        return output;
    };

//    public CreateTable(String name, String location, Map<String, String> columntypes, Map<String, Table> db) throws IOException {
//        createTable(name, location, columntypes, db);
//    }
}
