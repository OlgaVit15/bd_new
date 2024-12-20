package org.example.operations;

import org.example.objects.Table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Select {
    public List<List> select(Table table, String[] cols, List<List<String>> conds){
        List sel = new ArrayList<>();
        List<List> res = new ArrayList<>();
        String[] columns = {};
        if (conds == null) {
            if (cols == null){
                for (Map.Entry<String, ArrayList> column : table.collumns.entrySet()){
                    columns = new String[]{column.getKey()};
                }
                cols = columns;
            }
            int length = table.collumns.get(cols[0]).size();
            for (int i = 0; i < length; i++){
                sel.clear();
                for (int c = 0; c < cols.length; c++) {
                    if (table.collumns.get(cols[c]).size() == 0){
                        sel.add("null");
                    }
                    else {
                        Object obj = table.collumns.get(cols[c]).get(i);
                        sel.add(obj);
                    }
                }
                List buff = new ArrayList<>();
                buff.addAll(sel);
                res.add(buff);
            }

        }
        else {
            List<Integer> indexes = new ArrayList<>();
            Filter filter = new Filter();
            indexes = filter.filter(table, conds);
            indexes.sort(Comparator.naturalOrder());
            ArrayList bufferlist = new ArrayList<>();
            for (int index : indexes) {
                sel.clear();
                for (int i = 0; i < cols.length; i++) {
                    sel.add(table.collumns.get(cols[i]).get(index));
                }
                List buff = new ArrayList<>();
                buff.addAll(sel);
                res.add(buff);
            }
        }
        return res;
    }
}
