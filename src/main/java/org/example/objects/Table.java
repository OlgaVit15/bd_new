package org.example.objects;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.example.operations.Delete;
import org.example.operations.Insert;
import org.example.operations.Select;
import org.example.operations.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Table {
    @JsonProperty("table_name")
    public String table_name;
    @JsonProperty("json")
    public String path = this.table_name + ".json";
    @JsonProperty("columntypes")
    public Map<String, String> columntypes;
    @JsonProperty("collumns")
    public Map<String, ArrayList> collumns = new HashMap<>();
//    public void insert(Table table, Map<String, String[]> values) {
//        Insert ins = new Insert();
//        ins.insert(table, values);
//    }
//    public void update(Table table, List<List<String>> upd, List<List<String>> conds){
//        Update update = new Update();
//        update.update(table, upd, conds);
//    }
//    public void delete(Table table, List<List<String>> conds){
//        Delete delete = new Delete();
//        delete.delete(table, conds);
//    }
//    public List<List<String>> select(Table table, String[] cols, List<List<String>> conds){
//        Select select = new Select();
//        List<List<String>> selected = select.select(table, cols, conds);
//        return selected;
//    }
}