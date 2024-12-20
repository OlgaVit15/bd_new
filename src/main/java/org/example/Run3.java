package org.example;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.crud.Saver;
import org.example.objects.DataBase;
import org.example.objects.Table;
import org.example.operations.CreateDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

@EnableScheduling
@SpringBootApplication
@RestController
public class Run3 {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping("/")
//    @ResponseBody
//    public String check (){
//        return "connected";
//    }
//
//    public boolean checkPort(String host, int port) {
//        try (Socket socket = new Socket()) {
//            // Устанавливаем таймаут на 2000 мс (2 секунды)
//            socket.connect(new InetSocketAddress(host, port), 2000);
//            return true; // Порт доступен
//        } catch (IOException e) {
//            return false; // Порт недоступен
//        }
//    }
//
//    @GetMapping("/bd")
//    @ResponseBody
//    public ResponseEntity<List<List>> contr(@RequestParam String query) throws IOException {
//        Log log = new Log();
//        int ind = query.indexOf(" ");
//        String command = query.substring(0, ind);
//
//        String url_m = "http://master:8080/query?query=";
//        String url_s  = "http://slave:8080/query?query=";
//        String url = null;
//        if (!command.equals("select")) {
//            if (checkPort("master", 8080)){
//                url = url_m;
//            }
//            else {
//                url = url_s;
//            }
//            url = url + query; // URL другого сервиса
//            List<List> response = restTemplate.getForObject(url, List.class);
//            return ResponseEntity.ok(response);
//        }
//        else {
//            if (checkPort("slave", 8080)){
//                url = url_s;
//            }
//            else {
//                url = url_m;
//            }
//            url = url + query; // URL другого сервиса
//            List<List> response = restTemplate.getForObject(url, List.class);
//            return ResponseEntity.ok(response);
//        }
//    }
//
//    @GetMapping("/query")
//    @ResponseBody
//    public ResponseEntity<List<List>> master (@RequestParam String query) throws IOException {
//        String res = null;
//        String[] attrs = query.split(" ");
//        String command = attrs[0].trim();
//        String table = null;
//        if (command.equals("insert") || command.equals("delete")){
//            table = attrs[2].trim();
//        }
//        else {
//            table = attrs[1].trim();
//        }
//        DataBase dataBase = new DataBase();
//        Map<String, Table> db = new HashMap<>();
//
//        if (new File(DataBase.dbPath).exists()) {
//            db = dataBase.init(DataBase.dbPath);
//        } else {
//            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
//            db = dataBase.db;
//        }
//
//        Query q = new Query();
//        List<List> response = new ArrayList<>();
//
//        if (command.equals("insert") || command.equals("update") || command.equals("delete")) {
//            q.query(db, query);
//            response = q.query(db, "commit table " + table);
//            if (response.get(0).get(0).equals("commited")) {
//                res = new Log().writeToLog(query);
//                return ResponseEntity.ok(response);
//            }
//            else {
//                res = "uncompleted";
//                List buff = new ArrayList<>();
//                buff.add(res);
//                response.add(buff);
//                return ResponseEntity.ok(response);
//            }
//        }
//        else if (command.equals("create")){
//            response = q.query(db, query);
//            if (response.get(0).get(0).equals("Table was created")) {
//                res = new Log().writeToLog(query);
//                return ResponseEntity.ok(response);
//            }
//            else {
//                res = "uncompleted";
//                List buff = new ArrayList<>();
//                buff.add(res);
//                response.add(buff);
//                return ResponseEntity.ok(response);
//            }
//        }
//        else {
//            response = q.query(db, query);
//            return ResponseEntity.ok(response);
//        }
//
//    }
//
//    @GetMapping("/getlog")
//    @ResponseBody
//    public ResponseEntity<List<String>> logGet (@RequestParam String nodenum) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> todo = new ArrayList<>();
//        List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
//        for (List<String> li : loglist){
//            for (int i = 1; i < li.size(); i++){
//                if (!li.get(i).equals(nodenum)){
//                    todo.add(li.get(0));
//                }
//            }
//        }
//       return ResponseEntity.ok(todo);
//    }
//
//    @GetMapping("/writelog")
//    @ResponseBody
//    public ResponseEntity<String> logWrite (@RequestParam String done) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
//        for (List<String> li : loglist){
//            if (li.get(0).equals(done)){
//                li.add("1");
//            }
//        }
//        String res = new Saver().saveFile(Log.log, loglist);
//        return ResponseEntity.ok(res);
//    }
//
//    @Scheduled(fixedRate = 80000)
//    @GetMapping("/replicate")
//    @ResponseBody
//    public String slave () throws IOException {
//        String url = "http://master:8080/getlog?nodenum=1";
//        String url_w = "http://master:8080/writelog?done=";
//        String slave_url = "http://slave:8080/query?query=";
//
//        String resp = null;
//        List<String> todo = restTemplate.getForObject(url, List.class);
//        for (String query : todo){
//            url = slave_url + query; // URL другого сервиса
//            String response = restTemplate.getForObject(url, String.class);
//            resp = restTemplate.getForObject(url_w+query, String.class);
//        }
//        return ResponseEntity.ok(resp).toString();
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(Run.class, args);
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
