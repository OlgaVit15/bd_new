package org.example;

import java.sql.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    String table_name;
    String[] cols;
    List<List<String>> cond;
    List<List<String>> upd;
    Map<String, String[]> insert_values;
    Map<String, String> types;
    String command;


    List<List<String>> conditions(String cond) {
        List<List<String>> ref = new ArrayList<>();
        String regex = "(and|or|,)"; // Регулярное выражение для поиска слов "слово" с цифрой
        List<String> le = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cond);
        while (matcher.find()) {
            le.add(matcher.group());
        }
        String[] c = cond.split("(and|or|,)");
        String reg = "(.*?)(>|<|=|!=|isnot|notlike|is|like)(.*)";
        Pattern pref = Pattern.compile(reg);
        for (String el : c) {
            Matcher mref = pref.matcher(el.replace(" ", ""));
            if (mref.find()) {
                List<String> pair = new ArrayList<>();
                pair.add(mref.group(1));
                pair.add(mref.group(3));
                pair.add(mref.group(2));
                ref.add(pair);
            } else {
                System.out.println("not found");
            }
        }
        if (le.size() != 0){
            if ((le.get(0).equals("and")) || (le.get(0).equals("or"))) {
                ref.add(le);
            }
        }
        return ref;
    }

    void parse(String input) {
        input = input.trim();
        String command = input.substring(0, input.indexOf(" "));
        switch (command) {
            case "select":
                String reg1 = command + "\s+(.*?)\s+from\s+(.*?)\s+(?:where\s+(.*))?";
                Pattern p1 = Pattern.compile(reg1);
                Matcher m1 = p1.matcher(input + " ");
                if (m1.find()) {
                    String col1 = m1.group(1).replace(" ", "");;
                    String tab1 = m1.group(2);
                    if (m1.group(3) != null){
                        String cond1 = m1.group(3);
                        this.cond = conditions(cond1);
                    }
                    else {
                        this.cond = null;
                    }
                    this.cols = col1.split(",");
                    this.table_name = tab1.trim();
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "update":
                String regexp = command + "\s+(.*?)\s+set\s+(.*?)(?:\s+where\s+(.*))?$";
                Pattern p2 = Pattern.compile(regexp);
                Matcher m2 = p2.matcher(input + " ");
                if (m2.find()) {
                    this.table_name = m2.group(1).trim();
                    String row2 = m2.group(2).replace(" ", "");
                    String cond2 = m2.group(3);
                    this.upd = conditions(row2);
                    if (cond2 != null){
                        this.cond = conditions(cond2);
                    }
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "insert":
                String reg3 = command + "\s+into\s+(.*?)\s+(?:\\((.*?)\\))?(?:\s+values(?:\s+)?\\((.*)\\))?(?:(?:\s+)?(.*))?";
                Pattern p3 = Pattern.compile(reg3);
                Matcher m3 = p3.matcher(input + " ");
                Map<String, String[]> ins = new HashMap<>();
                if (m3.find()) {
                    this.table_name = m3.group(1).trim();
                    String col3 = m3.group(2).replace(" ","");
                    this.cols = col3.split(",");
                    String[] val3 = m3.group(3).replace(" ","").split(",");
                    List<String[]> seq = new ArrayList<>();
                    Map<String, String[]> insert_values = new HashMap<>();
                    Pattern p = Pattern.compile("'(.*)'");
                    for (String el : val3){
                        el = el.trim();
                        Matcher m = p.matcher(el);
                        if (m.find()){
                            String type = "string";
                            String value = el;
                            seq.add(new String[]{type, value});
                        }
                        else if (el.equals("true") || el.equals("false")){
                            String type = "boolean";
                            String value = el;
                            seq.add(new String[]{type, value});
                        }
                        else if (!(m.find()) & !(el.contains(",") || el.contains("."))){
                            String type = "integer";
                            String value = el;
                            seq.add(new String[]{type, value});
                        }
                        else {
                            System.out.println("invalid type");
                        }
                    }
                    for (int i = 0; i < cols.length; i++){
                        insert_values.put(cols[i], seq.get(i));
                    }
                    this.insert_values = insert_values;
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "delete":
                String reg4 = command + "\s+from\s+(.*?)\s+(?:where\s+(.*))?$";
                Pattern p4 = Pattern.compile(reg4);
                Matcher m4 = p4.matcher(input + " ");
                if (m4.find()) {
                    String cond4 = m4.group(2);
                    this.table_name = m4.group(1);
                    if (cond4 != null) {
                        this.cond = conditions(cond4);
                    }
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "create":
                String reg5 = command + "\s+table\s+(.*?)\s+\\((.*?)\\)";
                Pattern p5 = Pattern.compile(reg5);
                Matcher m5 = p5.matcher(input);
                if (m5.find()) {
                    this.table_name = m5.group(1).trim();
                    String[] row5 = m5.group(2).split(",");
                    Map<String, String> types = new HashMap<>();
                    for (String el : row5){
                        el = el.trim();
                        String[] p = el.split("\s+");
                        types.put(p[0], p[1]);
                    }
                    this.types = types;
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "drop":
                String reg6 = command + "\s+table\s+(.*)";
                Pattern p6 = Pattern.compile(reg6);
                Matcher m6 = p6.matcher(input);
                if (m6.find()) {
                    this.table_name = m6.group(1).trim();
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
            case "commit":
                String reg7 = command + "\s+table\s+(.*)";
                Pattern p7 = Pattern.compile(reg7);
                Matcher m7 = p7.matcher(input);
                if (m7.find()) {
                    this.table_name = m7.group(1).trim();
                    this.command = command;
                } else {
                    System.out.println("not found");
                }
                break;
        }
    }
    Parse(String input){
        parse(input);
    }
}
