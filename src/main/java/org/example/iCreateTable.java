package org.example;

import org.example.objects.Table;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface iCreateTable {
    List<List> createTable(String name, String location, Map<String, String> columntypes, Map<String, Table> db) throws IOException;
    void save(Table table, Map<String, Table> db) throws IOException;
    }
