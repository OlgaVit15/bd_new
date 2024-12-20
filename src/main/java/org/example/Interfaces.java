package org.example;

import org.example.objects.Table;

interface ISerialize {
    void serialize();
    void deserialize ();
}
interface IAccess {
    //namespace[]
    boolean checkName(String ObjName);
    Table getAccess(String obj);
}

interface IDataOperations extends IAccess, ISerialize {
    void save ();
    void remove ();
    void load();
    String read();
    void search();
}

interface ICommands extends IDataOperations {
    void insert(Table t, String[] val);
    void update(Table t, String[] val);
    void delete(Table t, String[] val);
    void drop(Table t);
    void create (Table T);
    void select(Table T);
}

interface IQuery extends IParser{
    void queryAnalyze(String[] q);
}

interface IParser {
    String[] parse (String query);
}

interface IDb{
    void use();
}