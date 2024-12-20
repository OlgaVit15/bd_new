package org.example.helpers;

import org.codehaus.jackson.map.ObjectMapper;
import org.example.crud.Saver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Log {
    public static String log = "log.txt";
    ObjectMapper mapper = new ObjectMapper();
    Saver saver = new Saver();

    public String writeToLog(String query) throws IOException {
        List<List<String>> logs;
        String res = null;
        if (new File(this.log).exists()){
            logs = mapper.readValue(new File(log), List.class);
            List<String> buff = new ArrayList<>();
            buff.add(query);
            buff.add("0");
            logs.add(buff);
            res = saver.saveFile(this.log, logs);
        }
        else {
            logs = new ArrayList<>();
            List<String> buff = new ArrayList<>();
            buff.add(query);
            buff.add("0");
            logs.add(buff);
            res = saver.saveFile(this.log, logs);
        }
        return res;
    }

    public List<List<String>> readLog() throws IOException {
        List<List<String>> log = new ArrayList<>();
        if (new File(Log.log).exists()) {
            log = mapper.readValue(new File(Log.log), List.class);

        }
        else{
            List<String> buff = new ArrayList<>();
            buff.add("log file does not exist");
            log.add(buff);
        }
        return log;
    }

    public String writeNode(String node, String path) throws IOException {
        Map<String, String> logs;
        String res = null;
        if (new File(path).exists()){
            logs = mapper.readValue(new File(path), Map.class);
            if (node.equals("master")) {
                logs.put(node, "0");
            }
            else{
                logs.put(node, "1");
            }
            res = saver.saveFile(path, logs);
        }
        else {
            logs = new HashMap<>();
            if (node.equals("master")) {
                logs.put(node, "0");
            }
            else{
                logs.put(node, "1");
            }
            res = saver.saveFile(path, logs);
        }
        return res;
    }

    public String rewriteNode(Map<String, String> logs, String path) throws IOException {
        String res = null;
        if (new File(path).exists()){
            res = saver.saveFile(path, logs);
        }
        else {
            res = "first write";
        }
        return res;
    }

    public Map<String, String> readNode(String path) throws IOException {
        Map<String, String> nodes;
        if (new File(path).exists()) {
            nodes = mapper.readValue(new File(path), Map.class);
        }
        else {
            nodes = new HashMap<>();
        }
        return nodes;
    }

    public String changeNodeState(String node, String state, String path) throws IOException {
        Map<String, String> nodes;
        String res = null;
        if (new File(path).exists()){
            nodes = mapper.readValue(new File(path), Map.class);
            if (!nodes.get(node).equals(state)) {
                nodes.remove(node);
                nodes.put(node, state);
                res = saver.saveFile(path, nodes);
            }
        }
        else {
            res = "there are no nodes";
        }
        return res;
    }

    public List<String> readState (String path) throws IOException {
        List<String> nodes;
        if (new File(path).exists()) {
            nodes = mapper.readValue(new File(path), List.class);
        }
        else {
            nodes = new ArrayList<>();
        }
        return nodes;
    }
}
