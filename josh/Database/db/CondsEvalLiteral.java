package db;

/**
 * Created by joshgoldman on 3/3/17.
 */
public class CondsEvalLiteral {
    public static void eval(Table table, Table newTable, String[] condition) {
        try {


            if (condition[1].compareTo("==") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                    if (!table.map.get(condition[0] + " string").get(i).equals(new ColumnItem(condition[2].toString()
                    ).createColumnItem(condition[2].toString()))) {
                        bool = false;
                    }
                    if (bool) {
                        for (int j = 0; j < table.map.keySet().toArray().length; j += 1) {
                            if (table.map.keySet().toArray()[j] == "NaN") {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(), new NaN());
                            } else {
                                newTable.addItem(table.map.keySet().toArray()[j].toString(),
                                        new ColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input).createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get(i).input)
                                                .createColumnItem(table.map.get(table.map.keySet().toArray()[j]).get
                                                        (i).input));
                            }

                        }
                    }
                }

            } else if (condition[1].compareTo(">") == 0) {

                for (int i = 0; i < table.numRows(); i += 1) {
                    boolean bool = true;
                    table.map.get(condition[0] + " string").get(i).input = table.map.get(condition[0] + " string").get(i).input.toString();
                    if (!table.map.get(condition[0] + " string").get(i).greaterThan(new ColumnItem(condition[2].toString()).createColumnItem(condition[2].toString()))) {
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
                    if (!table.map.get(condition[0] + " string").get(i).greaterThanEqualTo(new ColumnItem(condition[2].toString()).createColumnItem(condition[2].toString()))) {
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
                    if (!table.map.get(condition[0] + " string").get(i).lessThan(new ColumnItem(condition[2].toString()).createColumnItem(condition[2].toString()))) {
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
                    if (!table.map.get(condition[0] + " string").get(i).lessThanEqualTo(new ColumnItem(condition[2].toString()).createColumnItem(condition[2].toString()))) {
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
                    if (!table.map.get(condition[0] + " string").get(i).notEquals(new ColumnItem(condition[2].toString()).createColumnItem(condition[2].toString()))) {
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
        } catch (NullPointerException | NumberFormatException e3) {

            try {
                if (condition[1].compareTo("==") == 0) {

                    for (int i = 0; i < table.numRows(); i += 1) {
                        boolean bool = true;
                        table.map.get(condition[0] + " float").get(i).input = Float.parseFloat(table.map.get(condition[0] + " float").get(i).input.toString());
                        if (!table.map.get(condition[0] + " float").get(i).equals(new ColumnItem((condition[2])).createColumnItem(condition[2]))) {
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
                        if (!table.map.get(condition[0] + " float").get(i).greaterThan(new ColumnItem((condition[2]))
                                .createColumnItem(condition[2]))) {
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
                        if (!table.map.get(condition[0] + " float").get(i).greaterThanEqualTo(new ColumnItem(
                                (condition[2])).createColumnItem(condition[2]))) {
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
                        if (!table.map.get(condition[0] + " float").get(i).lessThan(new ColumnItem(condition[2]).createColumnItem(condition[2]))) {
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
                        if (!table.map.get(condition[0] + " float").get(i).lessThanEqualTo(new ColumnItem
                                (condition[2]).createColumnItem(condition[2]))) {
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
                        if (!table.map.get(condition[0] + " float").get(i).notEquals(new ColumnItem(condition[2]).createColumnItem(condition[2]))) {
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
            } catch (NullPointerException | NumberFormatException e) {

                try {


                    if (condition[1].compareTo("==") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                            if (!table.map.get(condition[0] + " int").get(i).equals(new ColumnItem((condition[2])).createColumnItem(condition[2]))) {
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

                    } else if (condition[1].compareTo(">") == 0) {

                        for (int i = 0; i < table.numRows(); i += 1) {
                            boolean bool = true;
                            table.map.get(condition[0] + " int").get(i).input = Integer.parseInt(table.map.get(condition[0] + " int").get(i).input.toString());
                            if (!table.map.get(condition[0] + " int").get(i).greaterThan(new ColumnItem(
                                    (condition[2])).createColumnItem(condition[2]))) {
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
                            if (!table.map.get(condition[0] + " int").get(i).greaterThanEqualTo(new ColumnItem(
                                    (condition[2])).createColumnItem(condition[2]))) {
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
                            if (!table.map.get(condition[0] + " int").get(i).lessThan(new ColumnItem((condition[2]))
                                    .createColumnItem(condition[2]))) {
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
                            if (!table.map.get(condition[0] + " int").get(i).lessThanEqualTo(new ColumnItem(
                                    (condition[2])).createColumnItem(condition[2]))) {
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
                            if (!table.map.get(condition[0] + " int").get(i).notEquals(new ColumnItem((condition[2]))
                                    .createColumnItem(condition[2]))) {
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
                } catch (NullPointerException | NumberFormatException e5) {


                }
            }

        }
    }
}

