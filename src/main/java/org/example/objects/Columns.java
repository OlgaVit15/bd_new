package org.example.objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Columns {
    Map<String, String> columnTypes;
    public Map<String, ArrayList> columns = new HashMap<>();
    void columnset(Map<String, String> columntypes){
        for (Map.Entry<String,String> entry : columntypes.entrySet()){
            String name = entry.getKey();
            switch(columntypes.get(name)){
                case "integer" :
                    ArrayList<Integer> intcolumns = new ArrayList<>();
                    columns.put(name, intcolumns);
                    break;
                case "string" :
                    ArrayList<String> strcolumns = new ArrayList<>();
                    columns.put(name, strcolumns);
                    break;
                case "boolean":
                    ArrayList<Boolean> boolcolumns = new ArrayList<>();
                    columns.put(name, boolcolumns);
                    break;
            }
            this.columns = columns;
        }
    }
    public Columns(Map<String, String> types){
        this.columnTypes = types;
        columnset(types);
    }
}
