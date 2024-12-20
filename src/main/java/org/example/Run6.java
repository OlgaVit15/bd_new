package org.example;
import jakarta.servlet.http.HttpServletRequest;
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
public class Run6 {
//    String controller = "http://controller:8080/";
//    String path = "log_state.txt";
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @GetMapping("/register")
//    @ResponseBody
//    public String register (@RequestParam String host) throws IOException {
//        String res = restTemplate.getForObject(controller+"register_query?host=" + host, String.class);
//        return res;
//    }
//
//    @GetMapping("/register_query")
//    @ResponseBody
//    public String register_q (@RequestParam String host) throws IOException {
//        new Log().writeState(host, "nodes.txt");
//        return "node registered";
//    }
//
//    @GetMapping("/")
//    @ResponseBody
//    public String check (HttpServletRequest request){
//        String host = request.getServerName();
//        System.out.println(host);
//        if (host.substring(0, 5).equals("slave")) {
//            String con = restTemplate.getForObject(controller + "register?host=" + host, String.class);
//        }
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
//    @RequestMapping("/state")
//    @ResponseBody
//    public String writestate(@RequestParam String state) throws IOException {
//        new Log().writeState(state, this.path);
//        return "ok";
//    }
//
//    @GetMapping("/checkstate")
//    @ResponseBody
//    public ResponseEntity<String> checkstate(HttpServletRequest request) throws IOException {
//        String host_log = null;
//        String host_exec = null;
//        if (request.getServerName().equals("master")) {
//            host_log = "master";
//            host_exec = "slave";
//        }
//        else {
//            host_log = "slave";
//            host_exec = "master";
//        }
//        if (new File(this.path).exists()) {
//            List<String> states = new Log().readState(this.path);
//            String node_lstate = null;
//            if (states.size() == 1){
//                node_lstate = states.get(states.size() - 1);
//            }
//            else{
//                node_lstate = states.get(states.size() - 2);
//            }
//            if (node_lstate.equals("0")) {
//                String url_log = "http://" + host_log + ":8080/getlog_init";
//                String url_q = "http://" + host_exec + ":8080/query?query=";
//                List<String> todo = restTemplate.getForObject(url_log, List.class);
//                for (String query : todo) {
//                    String response = restTemplate.getForObject(url_q + query, String.class);
//                }
//            }
//        }
//        return ResponseEntity.ok("ready");
//    }
//
//    @GetMapping("/bd")
//    @ResponseBody
//    public ResponseEntity<List<List>> contr(@RequestParam String query, HttpServletRequest request) throws IOException {
//        System.out.println(new Log().readState("nodes.txt"));
//        String host = request.getServerName();
//        Log log = new Log();
//        int ind = query.indexOf(" ");
//        String command = query.substring(0, ind);
//        String url_state = null;
//
//        String url_m = "http://master:8080/";
//        String url_s  = "http://slave:8080/";
//        String url = null;
//        String state = null;
//        if (!command.equals("select")) {
//            if (checkPort("master", 8080)){
//                url = url_m;
//                if (checkPort("slave", 8080)){
//                    url_state = "http://slave:8080/state?state=1";
//                    restTemplate.getForObject(url_state, String.class);
//                    restTemplate.getForObject("http://slave:8080/checkstate", String.class);
//                }
//            }
//            else {
//                url = url_s;
//                if (checkPort("master", 8080)){
//                    url_state = "http://slave:8080/state?state=0";
//                    restTemplate.getForObject(url_state, String.class);
//                }
//            }
//
//            url = url + "query?query=" + query; // URL другого сервиса
//            List<List> response = restTemplate.getForObject(url, List.class);
//            return ResponseEntity.ok(response);
//        }
//        else {
//            if (checkPort("slave", 8080)){
//                url = url_s;
//                if (checkPort("master", 8080)){
//                    url_state = "http://master:8080/state?state=1";
//                    restTemplate.getForObject(url_state, String.class);
//                    restTemplate.getForObject("http://master:8080/checkstate", String.class);
//                }
//            }
//            else {
//                url = url_m;
//                if (checkPort("slave", 8080)){
//                    url_state = "http://master:8080/state?state=0";
//                    restTemplate.getForObject(url_state, String.class);
//                }
//            }
//
//            url = url + "query?query=" + query; // URL другого сервиса
//            List<List> response = restTemplate.getForObject(url, List.class);
//            return ResponseEntity.ok(response);
//        }
//    }
//
//    @GetMapping("/query")
//    @ResponseBody
//    public ResponseEntity<List<List>> master (@RequestParam String query) throws IOException {
//
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
//    @GetMapping("/getlog_init")
//    @ResponseBody
//    public ResponseEntity<List<String>> logGetInit () throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> todo = new ArrayList<>();
//        List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
//        for (List<String> li : loglist){
//            todo.add(li.get(0));
//        }
//        System.out.println(todo.toString());
//        return ResponseEntity.ok(todo);
//    }
//
//    @GetMapping("/getlog")
//    @ResponseBody
//    public ResponseEntity<List<String>> logGet (@RequestParam String nodenum) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> todo = new ArrayList<>();
//        List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
//        for (List<String> li : loglist){
//            if (!li.contains(nodenum)){
//                todo.add(li.get(0));
//            }
//        }
//        System.out.println(todo.toString());
//        return ResponseEntity.ok(todo);
//    }
//
//    @GetMapping("/writelog")
//    @ResponseBody
//    public ResponseEntity<String> logWrite (@RequestParam List<String> done) throws IOException {
//        String res = null;
//        if (new File(Log.log).exists()) {
//            ObjectMapper mapper = new ObjectMapper();
//            List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
//            String nodenum = done.get(done.size() - 1).substring(0, 1);
//            System.out.println(nodenum);
//            for (int j = 0; j < loglist.size(); j++) {
//                for (int i = 0; i < done.size(); i++) {
//                    System.out.println(loglist.get(0));
//                    String query = done.get(i).substring(1, done.get(i).length());
//                    if (loglist.get(j).get(0).equals(query)) {
//                        System.out.println(query);
//                        loglist.get(j).add(nodenum);
//                    }
//                    System.out.println(loglist.toString());
//                }
//            }
//            System.out.println(loglist.toString());
//            res = new Saver().saveFile(Log.log, loglist);
//        }
//        else {
//            res = "log has not been written";
//        }
//        return ResponseEntity.ok(res);
//    }
//
//    //@Scheduled(fixedRate = 70000)
//    @GetMapping("/slave")
//    public String slave (HttpServletRequest request) throws IOException {
//        String host = request.getServerName();
//        String nodenum = host.substring(5);
//        System.out.println(nodenum);
//        String url = "http://master:8080/getlog?nodenum=" + nodenum;
//        String url_w = "http://master:8080/writelog?done=";
//        String slave_url = "http://"+ host + ":8080/query?query=";
//
//        List<String> resp = new ArrayList<>();
//        List<String> todo = restTemplate.getForObject(url, List.class);
//        for (String query : todo) {
//            url = slave_url + query; // URL другого сервиса
//            String response = restTemplate.getForObject(url, String.class);
//            resp.add(query);
//        }
//        resp.add(nodenum);
//        String res = restTemplate.getForObject(url_w + resp, String.class);
//        return ResponseEntity.ok(res).toString();
//    }
//
//    @GetMapping("/getnodes")
//    @ResponseBody
//    public List<String> getNodes() throws IOException {
//        List <String> nodes = new Log().readState("nodes.txt");
//        return nodes;
//    }
//
//
//    @Scheduled(fixedRate = 80000)
//    @GetMapping("/replicate")
//    public String replicate (){
//        List<String> nodes = restTemplate.getForObject(controller+"getnodes", List.class);
//        for (String node : nodes) {
//            String slave_url = "http://" + node + ":8080/slave";
//            String res = restTemplate.getForObject(slave_url, String.class);
//        }
//        return ResponseEntity.ok("replicated").toString();
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
