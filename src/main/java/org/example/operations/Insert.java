package org.example.operations;

import org.example.objects.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Insert {
    int length = 0;
    String fill_known(Table table, Map<String, String[]> values) {
        String res = "init";
        for (Map.Entry<String, String> col : table.columntypes.entrySet()) {
            for (Map.Entry<String, String[]> val : values.entrySet()) {
                if (val.getKey().equals(col.getKey())) {
                    String name = val.getKey();
                    String type = val.getValue()[0];
                    Convertor convert = new Convertor();
                    String value = val.getValue()[1];
                    if (type.equals(col.getValue())) {
                        table.collumns.get(name).add(convert.convert(type, value).get(0));
                    } else {
                        res = "Unmatched value";
                    }
                }
            }
            if (res.equals("Unmatched value")){
                break;
            }
        }
        return res;
    }
    void fill_unknown(Table table) {
        for (Map.Entry<String, ArrayList> col : table.collumns.entrySet()) {
           if (this.length < col.getValue().size()){
               this.length = col.getValue().size();
           }
        }
        for (Map.Entry<String, ArrayList> col : table.collumns.entrySet()) {
            if (col.getValue().size() < this.length) {
                String name = col.getKey();
                table.collumns.get(name).add("null");
            }
        }
    }
    public List<List> insert(Table table, Map<String, String[]> values){
        String fin = fill_known(table, values);
        if (fin.equals("Unmatched value")){
            System.out.println("Unmatched value");
        }
        else {
            fill_unknown(table);
        }
        List out = new ArrayList();
        List<List> output = new ArrayList<>();
        out.add("Rows were inserted");
        output.add(out);
        return output;
    }
}
