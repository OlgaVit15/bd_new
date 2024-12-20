package org.example;

import org.example.objects.Table;

import java.util.List;
import java.util.Map;

public interface IInsert {
    String fill_known(Table table, Map<String, String[]> values);
    void fill_unknown(Table table);
    List<List> insert (Table table, Map<String, String[]> values);
}
