package org.example;
import jakarta.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.example.crud.Saver;
import org.example.helpers.Checker;
import org.example.helpers.Log;
import org.example.helpers.NodeDefiner;
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
import java.util.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
@RestController
public class Run8 {
    String controller = "http://controller:8080/";
    String master_node = "http://master:8080/";
    String path = "log_state.txt";
    String nodepath = "node.txt";
    String valid = "valid.txt";
    NodeDefiner nodeDefiner = new NodeDefiner();
    Log log = new Log();
    Checker ch = new Checker();

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/register")
    @ResponseBody
    public String register (@RequestParam String host) throws IOException {
        String res = restTemplate.getForObject(controller+"register_query?host=" + host, String.class);
        return res;
    }

    @GetMapping("/register_query")
    @ResponseBody
    public String register_q (@RequestParam String host) throws IOException {
        if ((host.substring(0, 5).equals("slave") & ch.checkPort(host, 8080)) || (host.equals("master") & ch.checkPort(host, 8080))) {
            log.writeNode(host, nodepath);
        }
        return "node registered";
    }

    @GetMapping("/")
    @ResponseBody
    public String check (HttpServletRequest request){
        String host = request.getServerName();
        String con = restTemplate.getForObject(controller + "register?host=" + host, String.class);
        return "connected";
    }


    @GetMapping("/bd")
    @ResponseBody
    public ResponseEntity<List<List>> contr(@RequestParam String query) throws IOException {
        String slave = null;
        String master = "master";

        System.out.println(log.readNode(nodepath));
        if (new File(nodepath).exists()) {
            List<String> nodes = restTemplate.getForObject(controller + "getnodes", List.class);
            slave = nodeDefiner.nodeDef(query, nodes);
        }
        else {
            slave = "master";
            List<List> output = new ArrayList<>();
            List out = new ArrayList<>();
            out.add("nodes should be registered");
            output.add(out);
            return ResponseEntity.ok(output);
        }

        System.out.println(master);

        if (ch.checkPort(master, 8080)){
            master = "master";
        }
        else {
            Map<String, String> nodes = log.readNode(nodepath);
            nodes.remove("master");
            log.rewriteNode(nodes, nodepath);
            if (slave.equals("master")){
                List<String> ss = restTemplate.getForObject(controller + "getnodes", List.class);
                slave = nodeDefiner.nodeDef(query, ss);
            }
            master = slave;
        }

        System.out.println(master);
        int ind = query.indexOf(" ");
        String command = query.substring(0, ind);

        String url_m = "http://" + master + ":8080/";
        String url_s  = "http://" + slave + ":8080/";
        String url = null;

        System.out.println(url_m);

        if (!command.equals("select")) {
            if (master.equals("master")){
                log.changeNodeState(master, "1", nodepath);
            }
//            if (ch.checkPort("master", 8080)){
//                url = url_m;
//            }
//            else {
//                Map<String, String> nodes = log.readNode(nodepath);
//                nodes.remove("master");
//                log.rewriteNode(nodes, nodepath);
//                if (if )
//                url = url_s;
//            }
            url = url_m;

            url = url + "query?query=" + query; // URL другого сервиса
            List<List> response = restTemplate.getForObject(url, List.class);
            return ResponseEntity.ok(response);
        }
        else {
            url = url_s;

            url = url + "query?query=" + query; // URL другого сервиса
            List<List> response = restTemplate.getForObject(url, List.class);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/query")
    @ResponseBody
    public ResponseEntity<List<List>> master (@RequestParam String query, HttpServletRequest request) throws IOException {
        System.out.println("this is " + request.getServerName());

        String res = null;
        String[] attrs = query.split(" ");
        String command = attrs[0].trim();
        String table = null;
        if (command.equals("insert") || command.equals("delete")){
            table = attrs[2].trim();
        }
        else {
            table = attrs[1].trim();
        }
        DataBase dataBase = new DataBase();
        Map<String, Table> db = new HashMap<>();

        if (new File(DataBase.dbPath).exists()) {
            db = dataBase.init(DataBase.dbPath);
        } else {
            dataBase = new CreateDatabase().createDB(DataBase.dbPath);
            db = dataBase.db;
        }

        Query q = new Query();
        List<List> response = new ArrayList<>();

        if (command.equals("insert") || command.equals("update") || command.equals("delete")) {
            q.query(db, query);
            response = q.query(db, "commit table " + table);
            if (response.get(0).get(0).equals("commited")) {
                res = log.writeToLog(query);
                return ResponseEntity.ok(response);
            }
            else {
                res = "uncompleted";
                List buff = new ArrayList<>();
                buff.add(res);
                response.add(buff);
                return ResponseEntity.ok(response);
            }
        }
        else if (command.equals("create")){
            response = q.query(db, query);
            if (response.get(0).get(0).equals("Table was created")) {
                res = log.writeToLog(query);
                return ResponseEntity.ok(response);
            }
            else {
                res = "uncompleted";
                List buff = new ArrayList<>();
                buff.add(res);
                response.add(buff);
                return ResponseEntity.ok(response);
            }
        }
        else {
            response = q.query(db, query);
            return ResponseEntity.ok(response);
        }

    }

//---------------РЕПЛИКАЦИЯ---------------------------------------------------------------------

    @GetMapping("/getlog")
    @ResponseBody
    public ResponseEntity<List<String>> logGet (@RequestParam String nodenum) throws IOException {
        //restTemplate.getForObject("http://slave" + nodenum + ":8080/validate?state=process", String.class);
        List<String> todo = new ArrayList<>();
        List<List<String>> loglist = log.readLog();
        for (List<String> li : loglist){
            if (!li.contains(nodenum)){
                todo.add(li.get(0));
                li.add(nodenum);
            }
        }
        System.out.println(todo.toString());
        return ResponseEntity.ok(todo);
    }

    @GetMapping("/writelog")
    @ResponseBody
    public ResponseEntity<String> logWrite (@RequestParam List<String> done) throws IOException {
        String res = null;
        if (new File(Log.log).exists()) {
            ObjectMapper mapper = new ObjectMapper();
            List<List<String>> loglist = mapper.readValue(new File(Log.log), List.class);
            String nodenum = done.get(done.size() - 1).substring(0, 1);
            System.out.println("nodenum="+nodenum);
            for (int j = 0; j < done.size(); j++) {
                System.out.println("find="+done.get(j));
                String query = null;
                if (j == 0){
                    query = done.get(j).substring(1, done.get(j).length());
                }
                else {
                    query = done.get(j);
                }
                for (int i = 0; i < loglist.size(); i++) {
                    System.out.println("inlog="+loglist.get(i).get(0));
                    System.out.println(done.get(j));
                    System.out.println(query);
                    if (loglist.get(i).get(0).equals(query)) {
                        System.out.println(query);
                        loglist.get(i).add(nodenum);
                    }
                    System.out.println("med "+loglist.toString());
                }
            }
            System.out.println("last " +loglist.toString());
            res = new Saver().saveFile(Log.log, loglist);
        }
        else {
            res = "log has not been written";
        }
        return ResponseEntity.ok(res);
    }


    @GetMapping("/slave")
    public String slave (HttpServletRequest request) throws IOException {
        //List<String> valids = log.readState(valid);
        String host = request.getServerName();
        String nodenum = host.substring(5);
        System.out.println(nodenum);

        String url = master_node + "getlog?nodenum=" + nodenum;
        String url_w = master_node + "writelog?done=";
        String slave_url = "http://"+ host + ":8080/query?query=";

        List<String> resp = new ArrayList<>();
        List<String> todo = restTemplate.getForObject(url, List.class);
        String res = null;

        //if (valids.size() == 0 || valids.get(valids.size()-1).equals("validated")) {
            int i = 0;
            while (todo.size() != 0) {
                System.out.println("todo: " + todo.toString());
                url = slave_url + todo.get(i);
                System.out.println(url);
                String response = restTemplate.getForObject(url, String.class);
                String buf = todo.get(i);
                resp.add(buf);
                todo.remove(i);
            }
            resp.add(nodenum);
            res = restTemplate.getForObject(url_w + resp, String.class);
        //}
        return ResponseEntity.ok(res).toString();
    }
//------------------------------------------------------------------------------------

    @GetMapping("/getnodes")
    @ResponseBody
    public List<String> getNodes() throws IOException {
        Map<String, String> nodes = log.readNode(nodepath);
        List<String> lnodes = new ArrayList<>();
        for (Map.Entry<String, String> node : nodes.entrySet()){
            if (node.getValue().equals("1")||node.getKey().equals("master")){
                lnodes.add(node.getKey());
            }
            else if (node.getKey().substring(0,6).equals("master") & node.getKey().equals("0")){
                lnodes.add(node.getKey());
            }
        }
        return lnodes;
    }

    @GetMapping("/validate")
    @ResponseBody
    public String validate(@RequestParam String state) throws IOException {
        //log.writeNode(state, valid);
        return "validated";
    }



    @Scheduled(fixedRate = 80000)
    @GetMapping("/replicate")
    public String replicate (){
        List<String> nodes = restTemplate.getForObject(controller+"getnodes", List.class);
        for (String node : nodes) {
            //if (host.equals("slave")) {
                String slave_url = "http://" + node + ":8080/slave";
                String res = restTemplate.getForObject(slave_url, String.class);
                //restTemplate.getForObject("http://" + node + ":8080/validate?state=validated", String.class);
        }
        return ResponseEntity.ok("replicated").toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Run.class, args);
    }

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
