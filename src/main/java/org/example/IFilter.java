package org.example;

import org.example.objects.Table;

import java.util.List;

public interface IFilter {
    List<Integer> filter(Table table, List<List<String>> conds);
}
