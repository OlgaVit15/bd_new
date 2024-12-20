package org.example;

import org.example.crud.Reader;
import org.example.objects.Table;
import org.example.operations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Query {
    List<List> query(Map<String, Table> db, String input) throws IOException {
        Parse p = new Parse(input);
        List<List> output = new ArrayList<>();

        switch(p.command){
            case "create":
                output = new CreateTable().createTable(p.table_name, p.table_name + ".json", p.types, db);
                break;
            case "drop":
                output = new Drop().dropTable(db.get(p.table_name), db);
                break;
            case "delete":
                output = new Delete().delete(db.get(p.table_name), p.cond);
                break;
            case "insert":
                output = new Insert().insert(db.get(p.table_name), p.insert_values);
                break;
            case "update":
                output = new Update().update(db.get(p.table_name), p.upd, p.cond);
                break;
            case "commit":
                output = new Commit().commit(db.get(p.table_name));
                break;
            case "select":
                output = new Select().select(db.get(p.table_name), p.cols, p.cond);
                break;
        }
        return output;
    }
}
