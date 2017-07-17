package db;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by joshgoldman on 3/6/17.
 */
public class Select {
    public static String select(String exprs, String tables, String conds, Database d) {


        String[] exprSplit = exprs.replaceAll(",", " , ").replaceAll("\\s+", " ").split(" , ");
        String[] tablesList = tables.replaceAll(",", " , ").replaceAll("\\s+", " ").split(" , ");
        ArrayDeque<Table> tableDeque = new ArrayDeque<>();

        if (!(exprs.contains("*")) && tablesList.length >= 2) {
            Table joiner = (Table) d.getData().get(tablesList[0]);
            for (int i = 1; i < tablesList.length; i += 1) {
                joiner = d.join(joiner,(Table) d.getData().get(tablesList[i]), "helper");

            }
            d.getData().put("helper", joiner);
            return select(exprs, "helper", conds, d);
        }


        for (String expr : exprSplit) {

            Table t = new Table(expr);
            try {


                if (expr.compareTo("*") == 0 && tablesList.length > 1) {
                    t = d.join((Table) d.getData().get(tablesList[0]), (Table) d.getData().get(tablesList[1]), expr);


                    if (tablesList.length > 2) {
                        for (String table : Arrays.copyOfRange(tablesList, 2, tablesList.length)) {
                            t = d.join(t, (Table) d.getData().get(table), expr);

                        }
                    }


                } else if (expr.compareTo("*") == 0 && tablesList.length == 1) {
                    t = (Table) d.getData().get(tablesList[0]);
                } else if (expr.contains("+") || expr.contains("*") || expr.contains("/") || expr.contains("-")) {
                    String[] operate = expr.split(" as ");


                    if (operate[0].contains("+")) {
                        String[] add = operate[0].replaceAll(" ", "").split("\\+", 2);
                        t = new Table(operate[1]);

                        try {
                            if ((d.getData().get(tablesList[0]).map.get(add[0] + " int") != null) || (d
                                    .getData().get(tablesList[1]).map.get(add[0] + " int") != null)) {
                                t.addColumn(operate[1] + " int");
                            }

                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {


                                String j = d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i)
                                        .input.toString();
                                String k = d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i)
                                        .input.toString();


                                t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).add(new
                                                ColumnItem(k)
                                                .createColumnItem(k),
                                        new ColumnItem.intAdder()));
                            }
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            try {
                                if ((d.getData().get(tablesList[0]).map.get(add[0] + " int") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " int") != null)) {
                                    t.addColumn(operate[1] + " int");
                                }

                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                    String k = (add[1]);
                                    t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                            new ColumnItem.intAdder()));


                                }
                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e1) {
                                try {
                                    if ((d.getData().get(tablesList[0]).map.get(add[0] + " int") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " int") != null)) {
                                        t.addColumn(operate[1] + " int");
                                    }

                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                        String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input
                                                .toString());
                                        String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input
                                                .toString());

                                        t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                new ColumnItem.intAdder()));
                                    }
                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e2) {
                                    try {
                                        if ((d.getData().get(tablesList[0]).map.get(add[0] + " int") != null)) {
                                            t.addColumn(operate[1] + " int");
                                        }

                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input
                                                    .toString());
                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input
                                                    .toString());

                                            t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                    new ColumnItem.intAdder()));
                                        }
                                    } catch (NullPointerException e3) {
                                        try {
                                            if ((d.getData().get(tablesList[1]).map.get(add[0] + " int") != null)) {
                                                t.addColumn(operate[1] + " int");
                                            }

                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());

                                                t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.intAdder()));
                                            }
                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e4) {
                                            try {
                                                if ((d.getData().get(tablesList[0]).map.get(add[0] + " float") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " float") != null)) {
                                                    t.addColumn(operate[1] + " float");
                                                }

                                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatAdder()));
                                                }
                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e5) {
                                                try {
                                                    if ((d.getData().get(tablesList[0]).map.get(add[0] + " float") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " float") != null)) {
                                                        t.addColumn(operate[1] + " float");
                                                    }

                                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                        String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                        String k = (add[1]);

                                                        t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                                new ColumnItem.floatAdder()));


                                                    }
                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e6) {
                                                    try {
                                                        if ((d.getData().get(tablesList[0]).map.get(add[0] + " float") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " float") != null)) {
                                                            t.addColumn(operate[1] + " float");
                                                        }

                                                        for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                            String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());

                                                            t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                                    new ColumnItem.floatAdder()));
                                                        }
                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e7) {
                                                        try {

                                                            if (d.getData().get(tablesList[0]).map.get(add[0] + " float") != null) {
                                                                t.addColumn(operate[1] + " float");
                                                            }

                                                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());

                                                                t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                                        new ColumnItem.floatAdder()));
                                                            }
                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e8) {
                                                            try {

                                                                if ((d.getData().get(tablesList[0]).map.get(add[0] + " float") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " float") != null)) {
                                                                    t.addColumn(operate[1] + " float");
                                                                }

                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                    String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());

                                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k),
                                                                            new ColumnItem.floatAdder()));
                                                                }
                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e9) {
                                                                try {
                                                                    t.addColumn(operate[1] + " float");
                                                                    for (int i = 0; i < d.getData().get(tablesList[0])
                                                                            .numRows(); i += 1) {
                                                                        String j = (d.getData().get(tablesList[0]).map.get
                                                                                (add[0] + " float").get(i).input.toString());
                                                                        String k = (d.getData().get(tablesList[0]).map.get
                                                                                (add[1] + " int").get(i).input.toString());
                                                                        t.addItem(operate[1] + " float",
                                                                                new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k)
                                                                                        , new ColumnItem.floatAdder()));
                                                                    }
                                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e10) {
                                                                    try {
                                                                        t.addColumn(operate[1] + " float");
                                                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                            t.addItem(operate[1] + " float",
                                                                                    new ColumnItem(j).createColumnItem(j).add(new
                                                                                            ColumnItem(k), new
                                                                                            ColumnItem.floatAdder()));
                                                                        }
                                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e11) {
                                                                        try {
                                                                            t.addColumn(operate[1] + " float");
                                                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                                                                t.addItem(operate[1] + " float",
                                                                                        new ColumnItem(j).createColumnItem(j).add(new
                                                                                                ColumnItem(k), new
                                                                                                ColumnItem.floatAdder()));
                                                                            }
                                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e12) {
                                                                            try {
                                                                                t.addColumn(operate[1] + " float");
                                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                                                    t.addItem(operate[1] + " float",
                                                                                            new ColumnItem(j).createColumnItem(j).add(new
                                                                                                            ColumnItem(k),
                                                                                                    new ColumnItem.floatAdder()));
                                                                                }
                                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e13) {

                                                                                try {

                                                                                    if ((d.getData().get(tablesList[0]).map.get(add[0] + " string") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " string") != null)) {
                                                                                        t.addColumn(operate[1] + " string");
                                                                                    }

                                                                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                                        String j = d.getData().get(tablesList[0]).map.get(add[0] + " string").get(i).input.toString();
                                                                                        String k = d.getData().get(tablesList[1]).map.get(add[1] + " string").get(i).input.toString();
                                                                                        String x = new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.stringAdder()).input.toString().replaceAll("'", "");


                                                                                        t.addItem(operate[1] + " string", new ColumnItem("'" + x + "'"));
                                                                                    }
                                                                                } catch (NullPointerException |
                                                                                        ArrayIndexOutOfBoundsException | NumberFormatException e18) {
                                                                                    try {

                                                                                        if ((d.getData().get(tablesList[0]).map.get(add[0] + " string") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " string") != null)) {
                                                                                            t.addColumn(operate[1] + " string");
                                                                                        }

                                                                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                                            String j = d.getData().get(tablesList[1]).map.get(add[0] + " string").get(i).input.toString();
                                                                                            String k = d.getData().get(tablesList[1]).map.get(add[1] + " string").get(i).input.toString();

                                                                                            String x = new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.stringAdder()).input.toString().replaceAll("'", "");


                                                                                            t.addItem(operate[1] + " string", new ColumnItem("'" + x + "'"));


                                                                                        }
                                                                                    } catch (NullPointerException |
                                                                                            ArrayIndexOutOfBoundsException | NumberFormatException e19) {
                                                                                        try {

                                                                                            if ((d.getData().get(tablesList[0]).map.get(add[0] + " string") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " string") != null)) {
                                                                                                t.addColumn(operate[1] + " string");
                                                                                            }

                                                                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                                String j = d.getData().get(tablesList[1]).map.get(add[0] + " string").get(i).input.toString();
                                                                                                String k = d.getData().get(tablesList[0]).map.get(add[1] + " string").get(i).input.toString();

                                                                                                String x = new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.stringAdder()).input.toString().replaceAll("'", "");


                                                                                                t.addItem(operate[1] + " string", new ColumnItem("'" + x + "'"));
                                                                                            }
                                                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e20) {
                                                                                            try {

                                                                                                if ((d.getData().get(tablesList[0]).map.get(add[0] + " string") != null)) {
                                                                                                    t.addColumn(operate[1] + " string");
                                                                                                }

                                                                                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                                                    String j = d.getData().get(tablesList[0]).map.get(add[0] + " string").get(i).input.toString();
                                                                                                    String k = d.getData().get(tablesList[0]).map.get(add[1] + " string").get(i).input.toString();

                                                                                                    String x = new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.stringAdder()).input.toString().replaceAll("'", "");


                                                                                                    t.addItem(operate[1] + " string", new ColumnItem("'" + x + "'"));
                                                                                                }
                                                                                            } catch (NullPointerException |
                                                                                                    ArrayIndexOutOfBoundsException | NumberFormatException e15) {
                                                                                                try {

                                                                                                    if ((d.getData().get(tablesList[0]).map.get(add[0] + " string") != null) || (d.getData().get(tablesList[1]).map.get(add[0] + " string") != null)) {
                                                                                                        t.addColumn(operate[1] + " string");
                                                                                                    }

                                                                                                    for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                                        String j = d.getData().get(tablesList[0]).map.get(add[0] + " string").get(i).input.toString();
                                                                                                        String k = add[1];

                                                                                                        String x = new ColumnItem(j).createColumnItem(j).add(new ColumnItem(k).createColumnItem(k), new ColumnItem.stringAdder()).input.toString().replaceAll("'", "");


                                                                                                        t.addItem(operate[1] + " string", new ColumnItem("'" + x + "'"));
                                                                                                    }
                                                                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e14) {
                                                                                                    return "ERROR: CANNOT ADD";
                                                                                                }

                                                                                            }
                                                                                        }
                                                                                    }


                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (operate[0].contains("*")) {
                        String[] add = operate[0].replaceAll(" ", "").split("\\*", 2);
                        t = new Table(operate[1]);

                        try {
                            t.addColumn(operate[1] + " int");
                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                        new ColumnItem.intMultiplier()));
                            }
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            try {
                                t.addColumn(operate[1] + " int");
                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                    String k = (add[1]);
                                    t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                            new ColumnItem.intMultiplier()));


                                }
                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e1) {
                                try {
                                    t.addColumn(operate[1] + " int");
                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                        String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                        String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                        t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                new ColumnItem.intMultiplier()));
                                    }
                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e2) {
                                    try {
                                        t.addColumn(operate[1] + " int");
                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                            t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                    new ColumnItem.intMultiplier()));
                                        }
                                    } catch (NullPointerException e3) {
                                        try {
                                            t.addColumn(operate[1] + " int");
                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                        new ColumnItem.intMultiplier()));
                                            }
                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e4) {
                                            try {
                                                t.addColumn(operate[1] + " float");
                                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                            new ColumnItem.floatMultiplier()));
                                                }
                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e5) {
                                                try {
                                                    t.addColumn(operate[1] + " float");
                                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                        String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                        String k = (add[1]);
                                                        t.addItem(operate[1] + " float",
                                                                new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatMultiplier()));


                                                    }
                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e6) {
                                                    try {
                                                        t.addColumn(operate[1] + " float");
                                                        for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                            String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                            t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                                    new ColumnItem.floatMultiplier()));
                                                        }
                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e7) {
                                                        try {
                                                            t.addColumn(operate[1] + " float");
                                                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatMultiplier()));
                                                            }
                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e8) {
                                                            try {
                                                                t.addColumn(operate[1] + " float");
                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                    String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                                            new ColumnItem.floatMultiplier()));
                                                                }
                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e9) {
                                                                try {
                                                                    t.addColumn(operate[1] + " float");
                                                                    for (int i = 0; i < d.getData().get(tablesList[0])
                                                                            .numRows(); i += 1) {
                                                                        String j = (d.getData().get(tablesList[0]).map.get
                                                                                (add[0] + " float").get(i).input.toString());
                                                                        String k = (d.getData().get(tablesList[0]).map.get
                                                                                (add[1] + " int").get(i).input.toString());
                                                                        t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                                                new ColumnItem.floatMultiplier()));
                                                                    }
                                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e10) {
                                                                    try {
                                                                        t.addColumn(operate[1] + " float");
                                                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                            t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatMultiplier()));
                                                                        }
                                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e11) {
                                                                        try {
                                                                            t.addColumn(operate[1] + " float");
                                                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                                                                t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                                                        new ColumnItem.floatMultiplier()));
                                                                            }
                                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e12) {
                                                                            try {
                                                                                t.addColumn(operate[1] + " float");
                                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).multiply(new ColumnItem(k).createColumnItem(k),
                                                                                            new ColumnItem.floatMultiplier()));
                                                                                }
                                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e13) {
                                                                                return "ERROR: CANNOT MULTIPLY";
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (operate[0].contains("-")) {
                        String[] add = operate[0].replaceAll(" ", "").split("\\-", 2);
                        t = new Table(operate[1]);

                        try {
                            t.addColumn(operate[1] + " int");
                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                t.addItem(operate[1] + " int",
                                        new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.intSubtractor()));
                            }
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            try {
                                t.addColumn(operate[1] + " int");
                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                    String k = (add[1]);
                                    t.addItem(operate[1] + " int",
                                            new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.intSubtractor()));


                                }
                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e1) {
                                try {
                                    t.addColumn(operate[1] + " int");
                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                        String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                        String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                        t.addItem(operate[1] + " int",
                                                new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.intSubtractor()));
                                    }
                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e2) {
                                    try {
                                        t.addColumn(operate[1] + " int");
                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                            t.addItem(operate[1] + " int",
                                                    new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.intSubtractor()));
                                        }
                                    } catch (NullPointerException e3) {
                                        try {
                                            t.addColumn(operate[1] + " int");
                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                t.addItem(operate[1] + " int",
                                                        new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.intSubtractor()));
                                            }
                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e4) {
                                            try {
                                                t.addColumn(operate[1] + " float");
                                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                }
                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e5) {
                                                try {
                                                    t.addColumn(operate[1] + " float");
                                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                        String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                        String k = (add[1]);
                                                        t.addItem(operate[1] + " float",
                                                                new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));


                                                    }
                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e6) {
                                                    try {
                                                        t.addColumn(operate[1] + " float");
                                                        for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                            String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                            t.addItem(operate[1] + " float",
                                                                    new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                        }
                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e7) {
                                                        try {
                                                            t.addColumn(operate[1] + " float");
                                                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                t.addItem(operate[1] + " float",
                                                                        new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                            }
                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e8) {
                                                            try {
                                                                t.addColumn(operate[1] + " float");
                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                    String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                                    t.addItem(operate[1] + " float",
                                                                            new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                                }
                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e9) {
                                                                try {
                                                                    t.addColumn(operate[1] + " float");
                                                                    for (int i = 0; i < d.getData().get(tablesList[0])
                                                                            .numRows(); i += 1) {
                                                                        String j = (d.getData().get(tablesList[0]).map.get
                                                                                (add[0] + " float").get(i).input.toString());
                                                                        String k = (d.getData().get(tablesList[0]).map.get
                                                                                (add[1] + " int").get(i).input.toString());
                                                                        t.addItem(operate[1] + " float",
                                                                                new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                                    }
                                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e10) {
                                                                    try {
                                                                        t.addColumn(operate[1] + " float");
                                                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                            t.addItem(operate[1] + " float",
                                                                                    new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                                        }
                                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e11) {
                                                                        try {
                                                                            t.addColumn(operate[1] + " float");
                                                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                                                                t.addItem(operate[1] + " float",
                                                                                        new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                                            }
                                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e12) {
                                                                            try {
                                                                                t.addColumn(operate[1] + " float");
                                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                                                    t.addItem(operate[1] + " float",
                                                                                            new ColumnItem(j).createColumnItem(j).subtract(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatSubtractor()));
                                                                                }
                                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e13) {
                                                                                return "ERROR: CANNOT SUBTRACT";
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (operate[0].contains("/")) {
                        String[] add = operate[0].replaceAll(" ", "").split("\\/", 2);
                        t = new Table(operate[1]);

                        try {
                            t.addColumn(operate[1] + " int");

                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());

                                t.addItem(operate[1] + " int",
                                        new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.intDivider()));
                            }
                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                            try {
                                t.addColumn(operate[1] + " int");

                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                    String k = (add[1]);

                                    t.addItem(operate[1] + " int",
                                            new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.intDivider()));


                                }
                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e1) {
                                try {
                                    t.addColumn(operate[1] + " int");

                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                        String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                        String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());

                                        t.addItem(operate[1] + " int",
                                                new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.intDivider()));
                                    }
                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e2) {
                                    try {
                                        t.addColumn(operate[1] + " int");

                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                            t.addItem(operate[1] + " int", new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.intDivider()));
                                        }
                                    } catch (NullPointerException e3) {
                                        try {
                                            t.addColumn(operate[1] + " int");
                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " int").get(i).input.toString());
                                                String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                t.addItem(operate[1] + " int",
                                                        new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.intDivider()));
                                            }
                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e4) {
                                            try {
                                                t.addColumn(operate[1] + " float");
                                                for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                    t.addItem(operate[1] + " float", new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                }
                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e5) {
                                                try {
                                                    t.addColumn(operate[1] + " float");
                                                    for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                        String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                        String k = (add[1]);
                                                        t.addItem(operate[1] + " float",
                                                                new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));


                                                    }
                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e6) {
                                                    try {
                                                        t.addColumn(operate[1] + " float");
                                                        for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                            String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                            t.addItem(operate[1] + " float",
                                                                    new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                        }
                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e7) {
                                                        try {
                                                            t.addColumn(operate[1] + " float");
                                                            for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                t.addItem(operate[1] + " float",
                                                                        new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                            }
                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e8) {
                                                            try {
                                                                t.addColumn(operate[1] + " float");
                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                    String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " float").get(i).input.toString());
                                                                    t.addItem(operate[1] + " float",
                                                                            new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                                }
                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e9) {
                                                                try {
                                                                    t.addColumn(operate[1] + " float");
                                                                    for (int i = 0; i < d.getData().get(tablesList[0])
                                                                            .numRows(); i += 1) {
                                                                        String j = (d.getData().get(tablesList[0]).map.get
                                                                                (add[0] + " float").get(i).input.toString());
                                                                        String k = (d.getData().get(tablesList[0]).map.get
                                                                                (add[1] + " int").get(i).input.toString());
                                                                        t.addItem(operate[1] + " float",
                                                                                new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                                    }
                                                                } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e10) {
                                                                    try {
                                                                        t.addColumn(operate[1] + " float");
                                                                        for (int i = 0; i < d.getData().get(tablesList[0]).numRows(); i += 1) {
                                                                            String j = (d.getData().get(tablesList[0]).map.get(add[0] + " int").get(i).input.toString());
                                                                            String k = (d.getData().get(tablesList[0]).map.get(add[1] + " float").get(i).input.toString());
                                                                            t.addItem(operate[1] + " float",
                                                                                    new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                                        }
                                                                    } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e11) {
                                                                        try {
                                                                            t.addColumn(operate[1] + " float");
                                                                            for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                String j = (d.getData().get(tablesList[1]).map.get(add[0] + " float").get(i).input.toString());
                                                                                String k = (d.getData().get(tablesList[0]).map.get(add[1] + " int").get(i).input.toString());
                                                                                t.addItem(operate[1] + " float",
                                                                                        new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                                            }
                                                                        } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e12) {
                                                                            try {
                                                                                t.addColumn(operate[1] + " float");
                                                                                for (int i = 0; i < d.getData().get(tablesList[1]).numRows(); i += 1) {
                                                                                    String j = (d.getData().get(tablesList[0]).map.get(add[0] + " float").get(i).input.toString());
                                                                                    String k = (d.getData().get(tablesList[1]).map.get(add[1] + " int").get(i).input.toString());
                                                                                    t.addItem(operate[1] + " float",
                                                                                            new ColumnItem(j).createColumnItem(j).divide(new ColumnItem(k).createColumnItem(k).createColumnItem(k), new ColumnItem.floatDivider()));
                                                                                }
                                                                            } catch (NullPointerException | ArrayIndexOutOfBoundsException | NumberFormatException e13) {
                                                                                return "ERROR: CANNOT DIVIDE";
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    t = d.concatColumns(expr, tables);

                }
            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                return "ERROR: CANNOT SELECT";


            }


            Iterator<String> it = t.map.keySet().iterator();
            Iterator<String> ir = t.map.keySet().iterator();
            boolean bool = false;
            while (ir.hasNext()) {
                String j = ir.next();
                if (!t.map.get(j).isEmpty()) {
                    bool = true;
                }
            }

            if (bool) {
                while (it.hasNext()) {
                    String j = it.next();
                    if (t.map.get(j).isEmpty()) {
                        it.remove();
                    }
                }
            }


            tableDeque.addLast(t);


        }

        Table x = d.tableCombiner(tableDeque);
        return d.condsEval(x, conds).print();
    }
    
    
}

