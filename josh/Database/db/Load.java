package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by joshgoldman on 3/6/17.
 */
public class Load {
    public static String loadTable(String name, Database d) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(name + ".tbl"));
            d.getData().put(name, new Table(name));
            String columns1;
            if ((columns1 = bf.readLine()) == null) {
                return "ERROR: CANNOT LOAD EMPTY TABLE";
            }
            String[] columns = columns1.split(",");
            for (String i : columns) {
                if (i.split(" ").length != 2) {
                    return "ERROR: CANNOT LOAD";
                }
                if (!(i.split(" ")[1].compareTo("int") == 0)
                        && !(i.split(" ")[1].compareTo("float") == 0) && !(i
                        .split(" ")[1].compareTo("string") == 0)) {
                    return "ERROR: CANNOT CREATE TABLE";
                }
                d.getData().get(name).addColumn(i);
            }
            String line;
            while ((line = bf.readLine()) != null) {
                String[] row = line.split(",");
                if (row.length != columns.length) {
                    return "ERROR: CANNOT LOAD MALFORMED";
                }

                for (int i = 0; i < row.length; i += 1) {
                    if ((d.getData().get(name).map.keySet().toArray()[i].toString()) == null) {
                        return "ERROR: CANNOT LOAD";
                    }
                    if (row[i].toString().compareTo("NOVALUE") == 0) {
                        d.getData().get(name).addItem(d.getData().get(name).map.keySet().toArray()[i].
                                toString(), new NoValue());

                    } else if (row[i].toString().compareTo("NaN") == 0) {
                        d.getData().get(name).addItem(d.getData().get(name).map.keySet().toArray()[i].
                                toString(), new NaN());
                    } else {
                        if (d.getData().get(name).map.keySet().toArray()[i].toString().
                                split(" ")[1].compareTo("int") == 0) {
                            try {
                                Integer.parseInt(row[i]);
                            } catch (NumberFormatException e) {
                                return "ERROR: MALFORMED ENTRY";
                            }
                        }
                        if (d.getData().get(name).map.keySet().toArray()[i].toString().
                                split(" ")[1].compareTo("float") == 0) {
                            try {
                                Integer.parseInt(row[i]);
                                return "ERROR: CANNOT ADD INTS TO FLOAT COLUMN";
                            } catch (NumberFormatException e2) {

                                try {
                                    Float.parseFloat(row[i]);
                                } catch (NumberFormatException e) {
                                    return "ERROR: MALFORMED ENTRY";
                                }
                            }
                        }
                        if (d.getData().get(name).map.keySet().toArray()[i].toString().
                                split(" ")[1].compareTo("string") == 0) {
                            try {
                                Float.parseFloat(row[i]);
                                return "ERROR: CANNOT ADD NUMBER TO STRING COLUMN";
                            } catch (NumberFormatException e3) {
                                System.out.print("");
                            }
                        }

                        d.getData().get(name).addItem(d.getData().get(name).map.keySet().toArray()[i].toString(),
                                new ColumnItem(row[i]).createColumnItem(row[i]));
                    }
                }
            }
        } catch (IOException e) {
            return "ERROR: CANNOT LOAD";
        }
        return "";
    }

}
