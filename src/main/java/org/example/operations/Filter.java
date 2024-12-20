package org.example.operations;
import org.example.IFilter;
import org.example.objects.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Filter implements IFilter {
    public List<Integer> filter(Table table, List<List<String>> conds) {
        List<List<Integer>> rest = new ArrayList<>();
        List<Integer> inds = new ArrayList();
        List<Integer> antiinds = new ArrayList();
        int end;
        if (conds.get(conds.size() - 1).get(0).equals("and") || conds.get(conds.size() - 1).get(0).equals("or")){
            end = conds.size() - 1;
        }
        else {
            end = conds.size();
        }
        for (int i = 0; i < end; i++) {
            String colname = conds.get(i).get(0);
            String value = conds.get(i).get(1);
            String opeartor = conds.get(i).get(2);
            for (Map.Entry<String, ArrayList> entry : table.collumns.entrySet()) {
                if (entry.getKey().equals(colname)) {
                    ArrayList values = new ArrayList<>();
                    String type;
                    Convertor conv = new Convertor();
                    switch (table.columntypes.get(colname)) {
                        case "integer":
                            type = "integer";
                            values = conv.convert(type, value);
                            break;
                        case "string":
                            type = "string";
                            values = conv.convert(type, value);
                            break;
                        case "boolean":
                            type = "boolean";
                            values = conv.convert(type, value);
                    }
                    inds.clear();
                    antiinds.clear();
                    for (int j = 0; j < entry.getValue().size(); j++) {
                        if (entry.getValue().get(j).equals(values.get(0))) {
                            inds.add(j);
                        } else {
                            antiinds.add(j);
                        }
                    }
                }
                else{
                    continue;
                }
            }
            List<Integer> buff = new ArrayList<>();
            buff.addAll(inds);
            List<Integer> antybuff = new ArrayList<>();
            antybuff.addAll(antiinds);
            switch (opeartor) {
                case "=":
                    rest.add(buff);
                    break;
                case "!=":
                    rest.add(antybuff);
                    break;
            }
        }

        List<Integer> buff_r = new ArrayList<>();
        System.out.println(conds.toString());
        int size = conds.get(conds.size() - 1).size();
//        if (size > 1){
//            size = size - 1;
//        }
//        else {
//            size = size;
//        }
        String logoperator = conds.get(conds.size() - 1).get(0);
        int i = 0;
        while (i < rest.size()-1) {
            logoperator = conds.get(conds.size() - 1).get(i);
            if (i > 0) {
                rest.remove(i);
                i = i - 1;
            }
            System.out.println(rest.toString());
            System.out.println(logoperator);
            if (rest.get(i).size() > rest.get(i + 1).size()) {
                for (int f1 = 0; f1 < rest.get(i).size(); f1++) {
                    for (int f2 = 0; f2 < rest.get(i + 1).size(); f2++) {
                        if (logoperator.equals("and")) {
                            if (rest.get(i).get(f1) == rest.get(i + 1).get(f2)) {
                                buff_r.add(rest.get(i).get(f1));
                            }
                        } else {
                            int el;
                            int el2;
                            if (f2 < rest.get(i+1).size()){
                                el2 = rest.get(i+1).get(f2);
                            }
                            else {
                                el2 = -1;
                                f1++;
                            }
                            el = rest.get(i).get(f1);
                            System.out.println(el + " " + el2 + " " + buff_r);
                            if (el2 >= 0 & !(buff_r.contains(el2))){
                                buff_r.add(el2);
                            }
                            if (!(buff_r.contains(el))) {
                                buff_r.add(el);
                            }
                        }
                    }
                }
            }
            else {
                for (int f2 = 0; f2 < rest.get(i).size(); f2++) {
                    for (int f1 = 0; f1 < rest.get(i+1).size(); f1++) {
                        if (logoperator.equals("and")) {
                            if (rest.get(i).get(f1) == rest.get(i+1).get(f2)) {
                                buff_r.add(rest.get(i).get(f1));
                            }
                        }
                        else {
                            int el;
                            int el2;
                            if (f1 < rest.get(i).size()){
                                el = rest.get(i).get(f1);
                            }
                            else {
                                el = -1;
                                f2++;
                            }
                            el2 = rest.get(i+1).get(f2);
                            System.out.println(el + " " + el2 + " " + buff_r);
                            if (el >= 0 & !(buff_r.contains(el))){
                                buff_r.add(el);
                            }
                            if (!(buff_r.contains(el2))) {
                                buff_r.add(el2);
                            }
                        }
                    }
                }
            }

            rest.get(i).clear();
            rest.get(i).addAll(buff_r);
            i++;
            buff_r.clear();
        }
//        for (int j = 0; j < rest.size(); j++) {
//            if (j > 0) {
//                rest.remove(j+1);
//                j = j+1;
//            }
//            buff_r.clear();
//            String logoperator = conds.get(conds.size() - 1).get(j);
//            System.out.println(rest.toString());
//            System.out.println(logoperator);
//            if (logoperator.equals("and")) {
//                for (int el : rest.get(j)) {
//                    for (int el2 : rest.get(j+1)) {
//                        if (el == el2) {
//                            buff_r.add(el);
//                        }
//                    }
//                }
//                    rest.get(j).clear();
//                    rest.get(j).addAll(buff_r);
//            } else {
//                System.out.println("checkor" + rest.toString() + " " + rest.get(j));
//                System.out.println(rest.get(j) + " smth");
//                for (int el : rest.get(j)) {
//                    buff_r.add(el);
//                }
//                rest.get(j).clear();
//                rest.get(j).addAll(buff_r);
//                rest.get(j).addAll(buff_r);
//            }
//        }
//        for (int i = 0; i <= size; i++) {
//            buff_r.clear();
//            String logoperator = conds.get(conds.size() - 1).get(i);
//            System.out.println(rest.toString());
//            System.out.println(logoperator);
//            if (logoperator.equals("and")) {
//                for (int j = 0; j < rest.size()-1; j++) {
////
////                for (int j = 0; j < rest.size()-1; j++) {
////                    if (j > 0) {
////                        rest.remove(j-1);
////                        j = j-1;
////                    }
////                    for (int el : rest.get(j)) {
////                        for (int el2 : rest.get(j-1)) {
////                            if (el == el2) {
////                                buff_r.add(el);
////                            }
////                        }
////                    }
////                    rest.get(j).clear();
////                    rest.get(j).addAll(buff_r);
//                    //System.out.println("check" + rest.toString() + " " + rest.get(j) + " " + rest.get(j + 1));
//                    for (int el : rest.get(j)) {
//                        for (int el2 : rest.get(j + 1)) {
//                            if (el == el2) {
//                                buff_r.add(el);
//                            }
//                        }
//                    }
//                    rest.get(j).clear();
//                    rest.get(j).addAll(buff_r);
//                    rest.remove(j + 1);
//                }
//                } else {
//                for (int j = 0; j < rest.size(); j++) {
//                    System.out.println("checkor" + rest.toString() + " " + rest.get(j));
//                    if (j > 0) {
//                        rest.remove(j-1);
//                        j = j-1;
//                    }
//                    for (int el : rest.get(j)) {
//                        buff_r.add(el);
//                    }
//                    rest.get(j).clear();
//                    rest.get(j).addAll(buff_r);
//
//                }
//            }
//        }
        return rest.get(0);
    }
}
