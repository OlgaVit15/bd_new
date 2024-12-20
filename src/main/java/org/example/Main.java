package org.example;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.crud.Saver;
import org.example.objects.DataBase;
import org.example.objects.Table;
import org.example.operations.CreateDatabase;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
//    public static void main(String[] arg) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        Map<String, String> nodes = new HashMap<>();
//        nodes.put("slave1", "0");
//        nodes.put("slave2", "0");
//        new Saver().saveFile("nodes.txt", nodes);
//    }
//        for (List<String> li : loglist){
//            for (int i = 1; i < li.size(); i++){
//                if (!li.get(i).equals("1")){
//                    todo.add(li.get(0));
//                }
//            }
//        }
//        System.out.println(todo.toString());
//    }
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//        if (new File(DataBase.dbPath).exists()) {
//            db = dataBase.init(DataBase.dbPath);
//            Query q = new Query();
//            //q.query(db, "delete from cats");
//            String query = "insert into cats (id) values (2)";
//            q.query(db, query);
//            //q.query(db, "insert into cats (id, name) values (4, 'jerry')");
//            //q.query(db, "insert into cats (id) values (5)");
//            q.query(db, "commit table cats");
//            System.out.println(new Log().writeToLog(query));
//            System.out.println(q.query(db, "select id, name from cats").toString());
//        }
//    }
//        else {
//            //dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            //db = dataBase.db;
//            //Query q = new Query();
//            //q.query(db, "create table user (id integer, name string)");
////            q.query(db, "insert into user (id) values (1)");
////            q.query(db, "commit table user");
//            //System.out.println(q.query(db, "select id, name from user").toString());
//
//        }
//    }
}