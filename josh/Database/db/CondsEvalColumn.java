package db;

/**
 * Created by joshgoldman on 3/3/17.
 */
public class CondsEvalColumn {
    public static void eval(Table table, Table newTable, String[] condition) {
        try {

            if (condition[1].compareTo("==") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).equals(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j] == "NaN") {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new ColumnItem(table.map
                                        .get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map
                                        .get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo(">") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).greaterThan(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j] == "NaN") {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo(">=") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).greaterThanEqualTo(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j] == "NaN") {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo("<") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).lessThan(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo("<=") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).lessThanEqualTo(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo("!=") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                    if (!table.map.get(condition[0] + " int").get(i).notEquals(table.map.get(condition[2] + " int").get(i))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                            }

                        }
                    }
                }

            }

        } catch (NullPointerException e) {
            try {
                if (condition[1].compareTo("==") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).equals(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                } else if (condition[1].compareTo(">") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).greaterThan(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                } else if (condition[1].compareTo(">=") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).greaterThanEqualTo(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                } else if (condition[1].compareTo("<") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).lessThan(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                } else if (condition[1].compareTo("<=") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).lessThanEqualTo(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                } else if (condition[1].compareTo("!=") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).notEquals(table.map.get(condition[2] + " float").get(i))) {
                            bool = false;
                        }
                        if (bool) {
                            for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                if (table.map.keySet().toArray()[j] == "NaN") {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                } else {
                                    newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                            new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                }

                            }
                        }
                    }

                }


            } catch (NullPointerException e2) {
                try {
                    if (condition[1].compareTo("==") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                            if (!table.map.get(condition[0] + " string").get(i).equals(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    } else if (condition[1].compareTo(">") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;

                            if (!table.map.get(condition[0] + " string").get(i).greaterThan(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    } else if (condition[1].compareTo(">=") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                            if (!table.map.get(condition[0] + " string").get(i).greaterThanEqualTo(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    } else if (condition[1].compareTo("<") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                            if (!table.map.get(condition[0] + " string").get(i).lessThan(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    } else if (condition[1].compareTo("<=") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                            if (!table.map.get(condition[0] + " string").get(i).lessThanEqualTo(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    } else if (condition[1].compareTo("!=") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                            if (!table.map.get(condition[0] + " string").get(i).notEquals(table.map.get(condition[2] + " string").get(i))) {
                                bool = false;
                            }
                            if (bool) {
                                for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                    if (table.map.keySet().toArray()[j] == "NaN") {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                    } else {
                                        newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                    }

                                }
                            }
                        }

                    }
                } catch (NullPointerException e3) {
                    try {
                        if (condition[1].compareTo("==") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;
                                table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get
                                        (condition[0] + " float").get(i).input.toString());
                                if (!table.map.get(condition[0] + " float").get(i).equals(table.map.get(condition[2] + " " +
                                        "int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j] == "NaN") {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new ColumnItem(table.map
                                                    .get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map
                                                    .get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        } else if (condition[1].compareTo(">") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;
                                table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get
                                        (condition[0] + " float").get(i).input.toString());
                                if (!table.map.get(condition[0] + " float").get(i).greaterThan(table.map.get(condition[2] +
                                        " int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j] == "NaN") {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                    new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        } else if (condition[1].compareTo(">=") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;
                                table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get
                                        (condition[0] + " float").get(i).input.toString());
                                if (!table.map.get(condition[0] + " float").get(i).greaterThanEqualTo(table.map.get
                                        (condition[2] + " int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j] == "NaN") {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                    new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        } else if (condition[1].compareTo("<") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;

                                if (!table.map.get(condition[0] + " float").get(i).lessThan(table.map.get(condition[2] +
                                        " int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                    new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        } else if (condition[1].compareTo("<=") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;

                                if (!table.map.get(condition[0] + " float").get(i).lessThanEqualTo(table.map.get
                                        (condition[2] + " int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                    new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        } else if (condition[1].compareTo("!=") == 0) {

                            for (int i = 0; i < table.numRows(); i += 1) {
                                boolean bool = true;

                                if (!table.map.get(condition[0] + " float").get(i).notEquals(table.map.get(condition[2] +
                                        " int").get(i))) {
                                    bool = false;
                                }
                                if (bool) {
                                    for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                        if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                        } else {
                                            newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                    new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                        }

                                    }
                                }
                            }

                        }

                    } catch (NullPointerException e17) {
                        try {
                            if (condition[1].compareTo("==") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;
                                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                                    if (!table.map.get(condition[0] + " int").get(i).equals(table.map.get(condition[2] +
                                            " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j] == "NaN") {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new ColumnItem(table.map
                                                        .get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map
                                                        .get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            } else if (condition[1].compareTo(">") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;
                                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                                    if (!table.map.get(condition[0] + " int").get(i).greaterThan(table.map.get
                                            (condition[2] + " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j] == "NaN") {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            } else if (condition[1].compareTo(">=") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;
                                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                                    if (!table.map.get(condition[0] + " int").get(i).greaterThanEqualTo(table.map.get
                                            (condition[2] + " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j] == "NaN") {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            } else if (condition[1].compareTo("<") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;
                                    table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                                    if (!table.map.get(condition[0] + " int").get(i).lessThan(table.map.get(condition[2]
                                            + " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            } else if (condition[1].compareTo("<=") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;

                                    if (!table.map.get(condition[0] + " int").get(i).lessThanEqualTo(table.map.get
                                            (condition[2] + " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            } else if (condition[1].compareTo("!=") == 0) {

                                for (int i = 0; i < table.numRows(); i += 1) {
                                    boolean bool = true;

                                    if (!table.map.get(condition[0] + " int").get(i).notEquals(table.map.get(condition[2]
                                            + " float").get(i))) {
                                        bool = false;
                                    }
                                    if (bool) {
                                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                                            if (table.map.keySet().toArray()[j].toString().compareTo("NaN") == 0) {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                                            } else {
                                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input));
                                            }

                                        }
                                    }
                                }

                            }

                        } catch (NullPointerException e18) {
                        }


                    }
                }


            }
        }
    }
}

