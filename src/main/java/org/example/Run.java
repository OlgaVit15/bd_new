package org.example;
import org.example.objects.DataBase;
import org.example.objects.Table;
import org.example.operations.CreateDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
@RestController
public class Run {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping("/bd")
//    @ResponseBody
//    public ResponseEntity<String> contr(@RequestParam String query) throws IOException {
//        int ind = query.indexOf(" ");
//        String command = query.substring(0, ind);
//        if (!command.equals("select")) {
//            String url = "http://localhost:8081/query?query=" + query; // URL другого сервиса
//            String response = restTemplate.getForObject(url, String.class);
//            return ResponseEntity.ok(response);
//        }
//        else return ResponseEntity.ok("select");
//    }
//
//    @GetMapping("/query")
//    @ResponseBody
//    public String master (@RequestParam String query) throws IOException {
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//        if (new File(DataBase.dbPath).exists()) {
//            db = dataBase.init(DataBase.dbPath);
//        } else {
//            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            db = dataBase.db;
//        }
//        Query q = new Query();
//        String response = q.query(db, query).toString();
//        return ResponseEntity.ok(response).toString();
//    }
//
//    @RequestMapping("/slave")
//    @ResponseBody
//    public String slave (@RequestParam String query) throws IOException {
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//        if (new File(DataBase.dbPath).exists()) {
//            db = dataBase.init(DataBase.dbPath);
//        } else {
//            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            db = dataBase.db;
//        }
//        Query q = new Query();
//        String response = q.query(db, query).toString();
//        return ResponseEntity.ok(response).toString();
//    }
//
//    public static void main(String[] args) {
//          SpringApplication.run(Run.class, args);
//    }

}


//@SpringBootApplication
//@RestController
//class Run1 {
//    @RequestMapping(value = "/")
//    @ResponseBody
//    public ResponseEntity<String> callNode(@RequestParam String query) throws IOException {
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//        if (new File(DataBase.dbPath).exists()) {
//            db = dataBase.init(DataBase.dbPath);
//        } else {
//            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            db = dataBase.db;
//        }
//        Query q = new Query();
//        String response = q.query(db, query).toString();
//        return ResponseEntity.ok(response);
//    }
//}
//@RestController
//@SpringBootApplication
//public class Run {
//
//    @RequestMapping(value = "/")
//    @ResponseBody
//    public String q(@RequestParam String query, @RequestParam(required = false) String commit)throws IOException {
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//        if (new File(DataBase.dbPath).exists()){
//            db = dataBase.init(DataBase.dbPath);
//        }
//        else {
//            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            db = dataBase.db;
//        }
//        Query q = new Query();
//        if (query.substring(0, 6).equals("insert") || query.substring(0, 6).equals("update") ){
//            q.query(db, query).toString();
//            return q.query(db, commit).toString();
//        }
//        else if (query.equals("hello")){
//            return query;
//        }
//        else {
//            return q.query(db, query).toString();
//        }
//    }

//    public static void main(String[] args) {
//        SpringApplication.run(Run.class, args);
//    }
//}
