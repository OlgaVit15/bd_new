package org.example.crud;

import org.codehaus.jackson.map.ObjectMapper;
import org.example.objects.Table;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Reader {
    public Table tableread (String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Table table = mapper.readValue(new File(path), Table.class);
        return table;
    }

    public Map<String, String> fileread (String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> db = mapper.readValue(new File(path), Map.class);
        return db;
    }
}
