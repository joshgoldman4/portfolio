package db;

import java.util.*;

/**
 * Created by joshgoldman on 2/25/17.
 */
public class Table {
    String tableName;
    LinkedHashMap<String, Column> map;

    public Table(String name) {
        map = new LinkedHashMap<>();
        tableName = name;

    }

    public void addColumn(String columnName) {
        map.put(columnName, new Column(columnName));

    }

    public void addItem(String name, ColumnItem value) {
        map.get(name).addLast(value);


    }

    public String printRow(int index) {
        StringBuilder row = new StringBuilder();
        for (int i = 0; i < map.keySet().size() - 1; i += 1) {
            if (map.keySet().toArray()[i].toString().contains("float")) {
                if (map.get(map.keySet().toArray()[i]).findItem
                        (index).toString().compareTo("NOVALUE") == 0) {
                    row.append("NOVALUE");
                    row.append(",");

                } else if (map.get(map.keySet().toArray()[i]).findItem
                        (index).toString().compareTo("NaN") == 0) {
                    row.append("NaN");
                    row.append(",");

                } else {
                    row.append(String.format("%.3f", Float.parseFloat(map.get(map.keySet().toArray()[i]).findItem
                            (index).toString())));
                    row.append(",");
                }
            } else {
                row.append(map.get(map.keySet().toArray()[i]).findItem(index).toString() + ",");
            }

        }
        if (map.keySet().toArray()[map.keySet().size() - 1].toString().contains("float")) {
            if (map.get(map.keySet().toArray()[map.keySet().size
                    () - 1]).findItem
                    (index).toString().compareTo("NOVALUE") == 0) {
                row.append("NOVALUE");


            } else if (map.get(map.keySet().toArray()[map.keySet().size
                    () - 1]).findItem
                    (index).toString().compareTo("NaN") == 0) {
                row.append("NaN");


            } else {
                row.append(String.format("%.3f", Float.parseFloat(map.get(map.keySet().toArray()[map.keySet().size
                        () - 1]).findItem(index).toString())));
            }
        } else {
            row.append(map.get(map.keySet().toArray()[map.keySet().size() - 1]).findItem(index).toString());
        }
        return row.toString();


    }


    public String print() {


        StringBuilder x = new StringBuilder();
        try {
            int length = map.keySet().toArray().length;
            for (Object i : Arrays.copyOfRange(map.keySet().toArray(), 0, length - 1)) {
                x.append(i.toString() + ",");
            }
            x.append(map.keySet().toArray()[length - 1]);
            if (numRows() > 0) {
                x.append("\n");
                for (int j = 0; j < numRows() - 1; j += 1) {
                    x.append(printRow(j) + "\n");
                }
                x.append(printRow(numRows() - 1));
            }
        } catch (IllegalArgumentException e) {
            return "ERROR: NO TABLE TO PRINT";
        }
        return x.toString();
    }


    public int numRows() {
        return map.get(map.keySet().toArray()[0]).size();


    }

    public LinkedHashMap<String, ColumnItem> getRow(int index) {
        LinkedHashMap<String, ColumnItem> row = new LinkedHashMap<>();
        for (String column : map.keySet()) {
            row.put(column, map.get(column).get(index));
        }
        return row;

    }

    public void columnIntAdd(String column1, String column2, String newColumnName) {
        Adder<Integer> intAdd = new ColumnItem.intAdder();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).add(map.get(column2).get(i), intAdd)));
        }


    }

    public void columnStringAdd(String column1, String column2, String newColumnName) {
        Adder<String> stringAdd = new ColumnItem.stringAdder();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).add(map.get(column2).get(i), stringAdd)));
        }


    }

    public void columnFloatAdd(String column1, String column2, String newColumnName) {
        Adder<Float> floatAdd = new ColumnItem.floatAdder();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).add(map.get(column2).get(i), floatAdd)));
        }


    }

    public void columnFloatSubtract(String column1, String column2, String newColumnName) {
        Subtractor<Float> floatSub = new ColumnItem.floatSubtractor();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).subtract(map.get(column2).get(i), floatSub)));
        }


    }

    public void columnIntSubtract(String column1, String column2, String newColumnName) {
        Subtractor<Integer> intSub = new ColumnItem.intSubtractor();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).subtract(map.get(column2).get(i), intSub)));
        }


    }

    public void columnFloatMultiply(String column1, String column2, String newColumnName) {
        Multiplier<Float> floatMult = new ColumnItem.floatMultiplier();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).multiply(map.get(column2).get(i), floatMult)));
        }


    }

    public void columnIntMultiply(String column1, String column2, String newColumnName) {
        Multiplier<Integer> intMult = new ColumnItem.intMultiplier();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).multiply(map.get(column2).get(i), intMult)));
        }


    }

    public void columnFloatDivide(String column1, String column2, String newColumnName) {
        Divider<Float> floatDiv = new ColumnItem.floatDivider();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).divide(map.get(column2).get(i), floatDiv)));
        }


    }

    public void columnIntDivide(String column1, String column2, String newColumnName) {
        Divider<Integer> intDiv = new ColumnItem.intDivider();
        addColumn(newColumnName);
        for (int i = 0; i < numRows(); i += 1) {
            addItem(newColumnName, (map.get(column1).get(i).divide(map.get(column2).get(i), intDiv)));
        }


    }


    public static void main(String[] args) {


        System.out.println(String.format("%.3f", 1.5));


    }


}




