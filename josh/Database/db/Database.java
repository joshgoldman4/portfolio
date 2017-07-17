package db;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class Database {
    private LinkedHashMap<String, Table> data;

    public Database() {
        data = new LinkedHashMap<>();

    }


    public LinkedHashMap<String, Table> getData() {
        return this.data;
    }

    // Various common constructs, simplifies parsing.
    private static final String REST = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
            LOAD_CMD = Pattern.compile("load " + REST),
            STORE_CMD = Pattern.compile("store " + REST),
            DROP_CMD = Pattern.compile("drop table " + REST),
            INSERT_CMD = Pattern.compile("insert into " + REST),
            PRINT_CMD = Pattern.compile("print " + REST),
            SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW = Pattern.compile("(\\S"
            + "+"
            + ")\\s+"
            + "\\((\\S"
            + "+"
            + "\\s+"
            + "\\S+"
            + "\\s*"
            + "(?:,\\s*\\S+"
            + "\\s+"
            + "\\S+"
            + "\\s*)*)\\)"),
            SELECT_CLS = Pattern.compile("([^,]"
                    + "+?(?:,[^,]"
                    + "+?)*)\\s+from\\s"
                    + "+"
                    + "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s"
                    + "+where\\s+"
                    + "([\\w\\s+\\-*/'<>=!]"
                    + "+"
                    + "?(?:\\s"
                    + "+and\\s"
                    + "+"
                    + "[\\w\\s"
                    + "+\\-*/'<>=!]+?)*))?"),
            CREATE_SEL = Pattern.compile("(\\S"
                    + "+)\\s"
                    + "+as select\\s"
                    + "+"
                    + SELECT_CLS.pattern()),
            INSERT_CLS = Pattern.compile("(\\S+)\\s+values\\s+(.+?"
                    + "\\s*(?:,\\s*.+?\\s*)*)");

    public String transact(String query) {
        query = query.replaceAll("\\s+", " ");

        Matcher m;
        if ((m = CREATE_CMD.matcher(query)).matches()) {
            return createTable(m.group(1));
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            return printTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            return insertRow(m.group(1));
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            return dropTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            return storeTable(m.group(1));
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            return loadTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            return select(m.group(1));
        } else {
            return "ERROR: MALFORMED QUERY";
        }

    }

    private String createTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {
            return createNewTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {
            return createSelectedTable(m.group(1), m.group(2),
                    m.group(3), m.group(4));
        } else {
            return "ERROR: MALFORMED CREATE";
        }
    }

    private String createNewTable(String name, String[] cols) {
        Table table = new Table(name);
        data.put(name, table);
        for (String column : cols) {
            if (!column.contains("int") && !column.contains("float")
                    && !column.contains("string")) {
                return "ERROR: CANNOT CREATE TABLE";
            }
            table.addColumn(column);
        }
        return "";


    }

    private String createSelectedTable(String name, String exprs, String tables, String conds) {
        Table table = new Table(name);
        data.put(name, table);
        String selectResult = select(exprs, tables, conds);
        String[] columns = selectResult.split("\n")[0].split(",");
        for (String i : columns) {
            table.addColumn(i);
        }
        String[] result = selectResult.split("\n");
        for (int i = 1; i < result.length; i += 1) {
            insertRow(name + " values " + result[i]);
        }


        return "";
    }

    private String printTable(String name) {
        try {
            return data.get(name).print();
        } catch (NullPointerException e) {
            return "ERROR: CANNOT PRINT";
        }
    }

    private String insertRow(String expr) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            return "ERROR: MALFORMED INSERT";

        }

        try {
            for (int i = 0; i < data.get(m.group(1)).map.keySet().
                    toArray().length; i += 1) {
                Table table = data.get(m.group(1));
                String[] list = m.group(2).split(",");

                if (list[i].toString().compareTo("NaN")
                        == 0 || list[i].toString().
                        compareTo("NOVALUE")
                        == 0) {
                    table.addItem(table.map.keySet().toArray()[i].toString(),
                            new ColumnItem(list[i]).createColumnItem(list[i]));
                } else if (table.map.keySet().toArray()[i].toString().split(" ")[1].
                        compareTo("int") == 0) {

                    try {
                        Integer.parseInt(list[i]);
                        table.addItem(table.map.keySet().toArray()[i].toString(),
                                new ColumnItem(list[i]).createColumnItem(list[i]));
                    } catch (NumberFormatException e) {
                        return "ERROR: CANNOT ADD TO INT COLUMN";
                    }


                } else if (table.map.keySet().toArray()[i].toString().split(" ")[1].
                        compareTo("float") == 0) {
                    try {
                        Integer.parseInt(list[i]);
                        return "ERROR: CANNOT ADD INT TO FLOAT COLUMN";
                    } catch (NumberFormatException e) {
                        try {
                            Float.parseFloat(list[i]);
                            table.addItem(table.map.keySet().toArray()[i].toString(),
                                    new ColumnItem(list[i]).createColumnItem(list[i]));

                        } catch (NumberFormatException e2) {
                            return "ERROR: CANNOT ADD STRING TO FLOAT COLUMN";
                        }
                    }

                } else {
                    try {
                        Float.parseFloat(list[i]);
                        return "ERROR: CANNOT ADD NUMBER TO STRING COLUMN";
                    } catch (NumberFormatException e3) {
                        table.addItem(table.map.keySet().toArray()[i].toString(),
                                new ColumnItem(list[i]).createColumnItem(list[i]));
                    }
                }
            }


        } catch (NullPointerException e) {
            return "ERROR: CANNOT INSERT INTO";
        }
        return "";
    }

    private String dropTable(String name) {
        if (data.remove(name) == null) {
            return "ERROR: CANNOT DROP";
        } else {
            data.remove(name);
            return "";


        }

    }

    private String storeTable(String name) {
        try {
            File f = new File(name + ".tbl");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
            for (int i = 0; i < data.get(name).numRows(); i += 1) {
                bw.write(data.get(name).print().split("\n")[i] + "\n");
            }
            bw.write(data.get(name).print().split("\n")[data.get(name).numRows()]);
            bw.close();
            return "";
        } catch (IOException | NullPointerException e) {
            return "ERROR: CANNOT STORE";


        }
    }

    private String loadTable(String name) {
        return Load.loadTable(name, this);
    }

    private String select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            return "ERROR: MALFORMED SELECT";

        }

        return select(m.group(1), m.group(2), m.group(3));
    }

    private String select(String exprs, String tables, String conds) {
        return Select.select(exprs, tables, conds, this);
    }


    public Table sharedColumns(Table first, Table second, String name) {
        Table newTable = new Table(name);
        int i = 0;
        ArrayDeque<Column> sharedKeys = new ArrayDeque();
        for (String key : first.map.keySet()) {

            try {
                if (second.map.containsKey(key)) {
                    newTable.addColumn(key);
                }


            } catch (NoSuchElementException e) {
                System.out.print("");

            }
        }

        return newTable;

    }


    public Table joinColumns(Table first, Table second, String name) {
        Table shared = sharedColumns(first, second, name);
        for (String key : shared.map.keySet()) {
            shared.addColumn(key);
        }
        for (String key : first.map.keySet()) {
            if (!shared.map.containsKey(key)) {
                shared.addColumn(key);
            }
        }
        for (String key : second.map.keySet()) {
            if (!shared.map.containsKey(key)) {
                shared.addColumn(key);
            }
        }
        return shared;

    }

    public Table join(Table first, Table second, String name) {
        Table columns = joinColumns(first, second, name);
        Table shared = sharedColumns(first, second, name);
        if (shared.map.keySet().isEmpty()) {
            for (String column1 : columns.map.keySet()) {
                for (int j = 0; j < first.numRows(); j += 1) {
                    for (int i = 0; i < second.numRows(); i += 1) {
                        try {
                            columns.addItem(column1,
                                    new ColumnItem(first.map.get(column1).get(j).input).
                                            createColumnItem(first.map.get(column1).get(j).input));
                        } catch (NullPointerException e) {
                            columns.addItem(column1,
                                    new ColumnItem(second.map.get(column1).get(i).input).
                                            createColumnItem(second.map.get(column1).get(i).input));
                        }
                    }

                }

            }
        } else {


            int i = 0;
            while (i < first.numRows()) {
                int j = 0;
                while (j < second.numRows()) {
                    boolean condition = true;
                    for (String key : shared.map.keySet()) {
                        if ((first.getRow(i).get(key).
                                input.toString().compareTo(second.getRow(j).get(key).
                                input.toString())) != 0) {
                            condition = false;
                        }

                    }
                    if (condition) {
                        for (String key2 : columns.map.keySet()) {

                            if (first.getRow(i).containsKey(key2)) {
                                columns.addItem(key2,
                                        new ColumnItem(first.getRow(i).get(key2).input).
                                                createColumnItem(first.getRow(i).get(key2).input));
                            } else {
                                columns.addItem(key2,
                                        new ColumnItem(second.getRow(j).get(key2).input).
                                                createColumnItem(second.getRow(j).get(key2).input));
                            }
                        }
                    }
                    j += 1;
                }
                i += 1;


            }
        }
        return columns;
    }


    public Table condsEval(Table table, String conds) {
        if (conds == null) {
            return table;
        } else {
            String[] condsList = conds.split(" and ");
            String[] condition = condsList[0].split("'");
            String[] condOne = condition[0].split(" ");
            String[] conditioncopy;
            if (condition[0].toString().compareTo(condsList[0].toString()) != 0) {
                conditioncopy = new String[1 + condOne.length];
                for (int i = 0; i < conditioncopy.length - 1; i += 1) {
                    conditioncopy[i] = condOne[i];
                }
                conditioncopy[conditioncopy.length - 1] = condition[1];
            } else {
                conditioncopy = new String[condOne.length];
                for (int i = 0; i < conditioncopy.length; i += 1) {
                    conditioncopy[i] = condOne[i];
                }
            }

            Table newTable = new Table("temp");

            for (String column : table.map.keySet()) {
                newTable.addColumn(column);
            }

            if (table.map.get(conditioncopy[2] + " int") != null || table.map.
                    get(conditioncopy[2] + " float") != null
                    || table.map.get(conditioncopy[2] + " string") != null) {
                CondsEvalColumn.eval(table, newTable, conditioncopy);
            } else {
                CondsEvalLiteral.eval(table, newTable, conditioncopy);
            }

            String[] condsDecrease = conds.split(" and ", 2);
            String next;
            if (condsDecrease.length == 1) {
                next = null;
            } else {
                next = condsDecrease[1];
            }
            return condsEval(newTable, next);
        }

    }

    public Table concatColumns(String exprs, String tables) {
        String[] columnList = exprs.replaceAll(" ", "").split(",");
        String[] tablesList = tables.split(",");
        Table temp = new Table("Temp");
        for (int j = 0; j < tablesList.length; j += 1) {
            for (int i = 0; i < columnList.length; i += 1) {
                try {
                    temp.addColumn(data.get(tablesList[j]).
                            map.get(columnList[i] + " int").columnName);
                    for (int k = 0; k < data.get(tablesList[j]).
                            map.get(columnList[i] + " int").size(); k += 1) {
                        temp.addItem(data.get(tablesList[j]).
                                        map.get(columnList[i] + " int").columnName,
                                new ColumnItem(data.get(tablesList[j]).
                                        map.get(columnList[i] + " int").
                                        get(k).input).createColumnItem(data.get(tablesList[j]).
                                        map.get(columnList[i] + " int").get(k).input));
                    }
                } catch (NullPointerException e) {
                    try {
                        temp.addColumn(data.get(tablesList[j]).
                                map.get(columnList[i] + " float").columnName);
                        for (int k = 0; k < data.get(tablesList[j]).
                                map.get(columnList[i] + " float").
                                size(); k += 1) {
                            temp.addItem(data.get(tablesList[j]).
                                            map.get(columnList[i] + " float").columnName,
                                    new ColumnItem(data.get(tablesList[j]).
                                            map.get(columnList[i] + " float").get(k)
                                            .input).createColumnItem(data.get(tablesList[j]).
                                            map.get(columnList[i] + " float").get(k)
                                            .input));
                        }
                    } catch (NullPointerException e1) {
                        try {
                            temp.addColumn(data.get(tablesList[j]).
                                    map.get(columnList[i] + " string").columnName);
                            for (int k = 0; k < data.get(tablesList[j]).
                                    map.get(columnList[i] + " string").
                                    size(); k += 1) {
                                temp.addItem(data.get(tablesList[j]).
                                                map.get(columnList[i] + " string").columnName,
                                        new ColumnItem(data.get(tablesList[j]).
                                                map.get(columnList[i] + " string").get
                                                (k).input).createColumnItem
                                                (data.get(tablesList[j]).
                                                        map.get(columnList[i] + " string").get
                                                        (k).input));
                            }
                        } catch (NullPointerException e2) {
                            System.out.print("");

                        }
                    }

                }
            }
        }
        return temp;
    }


    public Table tableCombiner(ArrayDeque tableArray) {
        Table combined = new Table("combined");

        for (int i = 0; i < tableArray.size(); i++) {
            Table copy = (Table) tableArray.get(i);
            combined.map.putAll(copy.map);
        }
        return combined;
    }


    public static void main(String[] args) {
        Database d = new Database();
        Table a = new Table("a");
        Table b = new Table("b");

        a.addColumn("X Int");
        b.addColumn("X Int");
        b.addColumn("Z Int");
        a.addColumn("Y Int");
        a.addItem("X Int", new ColumnItem("2"));
        a.addItem("X Int", new ColumnItem("8"));
        a.addItem("X Int", new ColumnItem("13"));
        a.addItem("Y Int", new ColumnItem("5"));
        a.addItem("Y Int", new ColumnItem("3"));
        a.addItem("Y Int", new ColumnItem("7"));


        b.addItem("X Int", new ColumnItem("2"));
        b.addItem("X Int", new ColumnItem("8"));
        b.addItem("X Int", new ColumnItem("10"));
        b.addItem("Z Int", new ColumnItem("4"));
        b.addItem("Z Int", new ColumnItem("9"));
        b.addItem("Z Int", new ColumnItem("1"));
        a.print();
        b.print();
        Database x = new Database();
        x.transact("load t4");
        x.transact("load teams");

        x.transact("create table new as select TeamName + City as a from teams");
        x.transact("create table newnew as select a + City as b from new,teams");
        x.transact("print newnew");
        System.out.println(x.transact("select * from newnew where b > 'i'"));
        System.out.println("'i'".compareTo("a"));
        System.out.println("4".compareTo("5"));
        x.transact("load teams");
        x.transact("load records");
        x.transact("load t1");
        System.out.println(x.transact("select a-c as d, b+c as e from t1"));


    }
}




