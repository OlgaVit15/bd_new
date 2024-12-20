package org.example.operations;

import org.example.objects.Table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Update {
    public List<List> update(Table table, List<List<String>> upd, List<List<String>> conds) {

        int length = 0;

        if (conds == null) {

            for (Map.Entry<String, ArrayList> ent : table.collumns.entrySet()) {
                for (int i = 0; i < upd.size(); i++) {
                    if (ent.getKey().equals(upd.get(i).get(0))) {
                        length = ent.getValue().size();
                        ent.getValue().clear();
                        for (int j = 0; j < length; j++) {
                            ent.getValue().add(upd.get(i).get(1));
                        }
                    } else {
                        continue;
                    }
                }
            }

        } else {

            List<Integer> indexes = new ArrayList<>();
            Filter filter = new Filter();
            indexes = filter.filter(table, conds);
            indexes.sort(Comparator.naturalOrder());

            ArrayList bufferlist = new ArrayList<>();
            for (int index : indexes) {
                for (int i = 0; i < upd.size(); i++) {
                    for (Map.Entry<String, ArrayList> elem : table.collumns.entrySet()) {
                        String column_name = upd.get(i).get(0);
                        String updvalue = upd.get(i).get(1);
                        ArrayList updvalues = new ArrayList<>();

                        String type;
                        Convertor conv = new Convertor();
                        switch (table.columntypes.get(column_name)) {
                            case "integer":
                                type = "integer";
                                updvalues = conv.convert(type, updvalue);
                                break;
                            case "string":
                                type = "string";
                                updvalues = conv.convert(type, updvalue);
                                break;
                            case "boolean":
                                type = "boolean";
                                updvalues = conv.convert(type, updvalue);
                        }

                        if (elem.getKey().equals(column_name)) {

                            length = elem.getValue().size();

                            bufferlist.addAll(table.collumns.get(column_name).subList(index, length));
                            int del = index;
                            while(del < length) {
                                table.collumns.get(column_name).remove(del);
                                length = table.collumns.get(column_name).size();
                            }

                            table.collumns.get(column_name).add(updvalues.get(0));
                            if (bufferlist.size() != 0) {
                                bufferlist.remove(0);
                                table.collumns.get(column_name).addAll(bufferlist);
                            }

                            length = elem.getValue().size();
                            bufferlist.clear();

                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        List out = new ArrayList();
        List<List> output = new ArrayList<>();
        out.add("Rows were updated");
        output.add(out);
        return output;
    }
}
