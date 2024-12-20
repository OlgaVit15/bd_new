package org.example.operations;

import java.util.ArrayList;

public class Convertor {
    ArrayList convert (String type, String value) {
        ArrayList values = new ArrayList<>();
        switch (type) {
            case "integer":
                int in = Integer.parseInt(value);
                values.add(in);
                break;
            case "boolean":
                boolean bool = Boolean.parseBoolean(value);
                values.add(bool);
                break;
            case "string":
                String str = value;
                values.add(str);
                break;
        }
        return values;
    }
}
