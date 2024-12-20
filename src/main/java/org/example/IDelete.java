package org.example;

import org.example.objects.Table;

import java.util.List;

public interface IDelete {
    List<List> delete(Table table, List<List<String>> conds);
}
