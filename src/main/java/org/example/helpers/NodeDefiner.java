package org.example.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NodeDefiner {
    Log log = new Log();
    Checker checker = new Checker();
    public String nodeDef (String query, List<String> nodes) throws IOException {
        int n = nodes.size();
        String node = null;

        int hash = (int) (query.length() * 0.8 % n);

        for(int i = 0; i < n; i++) {
            if (hash == i || hash - i < 5) {
                node = nodes.get(i);
            }
            else if (i+1 < n){
                node = nodes.get(i+1);
            }
            else{
                node = nodes.get(i);
            }
        }

        if (!checker.checkPort(node, 8080)) {
            nodes.remove(node);
            //log.rewriteNode(nodes, path);
            if (!nodes.isEmpty()) {
                node = nodeDef(query, nodes);
            }
            else {
                node = "master";
            }
        }

        return node;
    }

}
