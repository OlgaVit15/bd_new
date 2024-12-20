package org.example.operations;

import org.example.IDelete;
import org.example.objects.Table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Delete implements IDelete {
    public List<List> delete(Table table, List<List<String>> conds){

        if (conds == null) {
            for (Map.Entry<String, ArrayList> entry : table.collumns.entrySet()) {
                entry.getValue().clear();
            }
        }
        else {
            List<Integer> indexes = new ArrayList<>();
            Filter filter = new Filter();
            indexes = filter.filter(table, conds);
            indexes.sort(Comparator.naturalOrder());
            System.out.println("ind" + indexes);
            int ind = -8;
            for (int i = 0; i < indexes.size(); i++) {
                for (Map.Entry<String, ArrayList> entry : table.collumns.entrySet()) {
                    ind = indexes.get(i);
                    if (ind > 0 & i > 0) {
                        ind = ind - 1;
                    }
                    System.out.println(indexes.get(i));
                    entry.getValue().remove(ind);
                    System.out.println(entry.getKey() + " " + entry.getValue().toString());
                }
            }
        }
        List out = new ArrayList();
        List<List> output = new ArrayList<>();
        out.add("Rows were deleted");
        output.add(out);
        return output;
    }

}
