package db;

import java.text.DecimalFormat;


/**
 * Created by joshgoldman on 2/25/17.
 */
public class ColumnItem<Type extends Comparable> {
    public Type input;


    public ColumnItem() {
        input = (Type) "NOVALUE";


    }

    public ColumnItem(Type i) {

        input = i;


    }


    public ColumnItem createColumnItem(Type i) {
        if (i.toString().compareTo("NOVALUE") == 0) {
            return new NoValue();
        } else if (i.toString().compareTo("NaN") == 0) {
            return new NaN();
        } else {
            return new ColumnItem(i);
        }
    }


    public void print() {
        System.out.println(input);

    }


    public static class stringAdder implements Adder {

        public ColumnItem<String> addItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem("");
            }

            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem((String) second.input);

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem((String) first.input);

            } else {
                return new ColumnItem((String) first.input + (String) second.input);

            }


        }
    }

    public static class intAdder implements Adder {

        public ColumnItem<Integer> addItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(second.input);

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(first.input);

            } else {
                return new ColumnItem(Integer.parseInt(first.input.toString()) + Integer.parseInt(second
                        .input.toString()));

            }


        }
    }

    public static class floatAdder implements Adder {


        public ColumnItem<Float> addItems(ColumnItem first, ColumnItem second) {

            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0.0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(second.input.toString());

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(first.input.toString());

            } else {
                double i = (Double.parseDouble(String.format("%.03f", Float.parseFloat(first.input.toString()))) +
                        (Double.parseDouble(String.format("%.03f", Float.parseFloat(second.input.toString())))));
                return new ColumnItem(i);

            }


        }
    }

    public static class intSubtractor implements Subtractor {

        public ColumnItem<Integer> subtractItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(second.input.toString());

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(first.input.toString());

            } else {
                return new ColumnItem(Integer.parseInt(first.input.toString()) - Integer.parseInt(second.input.toString
                        ()));

            }


        }
    }


    public static class floatSubtractor implements Subtractor {


        public ColumnItem<Float> subtractItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0.0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(second.input.toString());

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(first.input.toString());

            } else {
                double i = Double.parseDouble(String.format("%.03f", Float.parseFloat(first.input.toString()))) - Double.parseDouble(String.format("%.03f", Float.parseFloat(second.input.toString())));
                return new ColumnItem((float) i);

            }


        }
    }


    public ColumnItem add(ColumnItem other, Adder<Type> adder) {
        return adder.addItems(this, other);

    }

    public ColumnItem subtract(ColumnItem other, Subtractor<Type> subtractor) {
        return subtractor.subtractItems(this, other);

    }

    public ColumnItem multiply(ColumnItem other, Multiplier<Type> multiplier) {
        return multiplier.multiplyItems(this, other);

    }

    public ColumnItem divide(ColumnItem other, Divider<Type> divider) {
        return divider.divideItems(this, other);

    }


    public static class floatMultiplier implements Multiplier {


        public ColumnItem<Float> multiplyItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0.0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }

            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(0.0);

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(0.0);

            } else {
                double i = Double.parseDouble(String.format("%.03f", Float.parseFloat(first.input.toString()))) * Double
                        .parseDouble
                                (String.format
                                        ("%.03f", Float.parseFloat(second.input.toString())));
                return new ColumnItem((float) i);

            }


        }
    }

    public static class intMultiplier implements Multiplier {

        public ColumnItem<Integer> multiplyItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == noVal.getClass() && second.getClass() == noVal.getClass()) {
                return new ColumnItem(0);
            }
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(0);

            } else if (second.getClass() == noVal.getClass()) {
                return new ColumnItem(0);

            } else {
                return new ColumnItem(Integer.parseInt(first.input.toString()) * Integer.parseInt(second.input
                        .toString()));

            }


        }
    }


    public static class floatDivider implements Divider {


        public ColumnItem<Float> divideItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }

            if (second.getClass() == noVal.getClass()) {
                return new NaN();

            } else if (first.getClass() == noVal.getClass()) {
                return new ColumnItem((float) 0.0);

            } else {
                double i = Double.parseDouble(String.format("%.03f", Float.parseFloat(first.input.toString()))) / Double
                        .parseDouble
                                (String
                                        .format
                                                ("%.03f", Float.parseFloat(second.input.toString())));
                if (i == Double.POSITIVE_INFINITY || i == Double.NEGATIVE_INFINITY || i == Double.NaN) {
                    return new NaN();
                }
                return new ColumnItem((float) i);

            }


        }
    }

    public static class intDivider implements Divider {

        public ColumnItem divideItems(ColumnItem first, ColumnItem second) {
            ColumnItem noVal = new NoValue();
            ColumnItem nan = new NaN();
            if (first.getClass() == nan.getClass() || second.getClass() == nan.getClass()) {
                return new NaN();
            }
            if (second.getClass() == noVal.getClass()) {
                return new NaN();

            } else if (first.getClass() == noVal.getClass()) {
                return new ColumnItem(0);

            } else {
                try {
                    return new ColumnItem(Integer.parseInt(first.input.toString()) / Integer.parseInt(second.input
                            .toString()));
                } catch (ArithmeticException e) {
                    return new NaN();
                }
            }

        }

    }

    public boolean equals(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return true;
        } else if (this.getClass() == nan.getClass() || other.getClass() == nan.getClass()) {
            return false;
        } else if (this.input.getClass() == string.getClass()) {
            return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) == 0;
        } else {
            Double first = Double.parseDouble(this.input.toString());
            Double second = Double.parseDouble(other.input.toString());
            return first.compareTo(second) == 0;
        }
    }

    public boolean notEquals(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() || other.getClass() == nan.getClass()) {
            return true;
        } else if (this.input.getClass() == string.getClass()) {
            return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) != 0;
        } else {
            Double first = Double.parseDouble(this.input.toString());
            Double second = Double.parseDouble(other.input.toString());
            return first.compareTo(second) != 0;
        }

    }

    public boolean greaterThan(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass()) {
            return true;
        } else if (other.getClass() == nan.getClass()) {
            return false;
        } else {
            try {
                Double first = Double.parseDouble(this.input.toString());
                Double second = Double.parseDouble(other.input.toString());
                return first.compareTo(second) > 0;
            } catch (NumberFormatException e) {
                return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) > 0;
            }

        }
    }


    public boolean lessThan(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass()) {
            return false;
        } else if (other.getClass() == nan.getClass()) {
            return true;
        } else {
            try {
                Double first = Double.parseDouble(this.input.toString());
                Double second = Double.parseDouble(other.input.toString());
                return first.compareTo(second) < 0;
            } catch (NumberFormatException e) {
                return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) < 0;
            }
        }

    }

    public boolean lessThanEqualTo(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return true;
        } else if (this.getClass() == nan.getClass()) {
            return false;
        } else if (other.getClass() == nan.getClass()) {
            return true;
        } else {
            try {
                Double first = Double.parseDouble(this.input.toString());
                Double second = Double.parseDouble(other.input.toString());
                return first.compareTo(second) <= 0;
            } catch (NumberFormatException e) {
                return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) <= 0;
            }
        }

    }

    public boolean greaterThanEqualTo(ColumnItem other) {
        ColumnItem noVal = new NoValue();
        ColumnItem nan = new NaN();
        String string = "";
        if (this.getClass() == noVal.getClass() || other.getClass() == noVal.getClass()) {
            return false;
        } else if (this.getClass() == nan.getClass() && other.getClass() == nan.getClass()) {
            return true;
        } else if (this.getClass() == nan.getClass()) {
            return true;
        } else if (other.getClass() == nan.getClass()) {
            return false;
        } else {
            try {
                Double first = Double.parseDouble(this.input.toString());
                Double second = Double.parseDouble(other.input.toString());
                return first.compareTo(second) >= 0;
            } catch (NumberFormatException e) {
                return this.input.toString().replaceAll("'", "").compareTo(other.input.toString().replaceAll("'", "")) >= 0;
            }
        }

    }


}




