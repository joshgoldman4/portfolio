package db;

/**
 * Created by joshgoldman on 2/26/17.
 */
public class NoValue extends ColumnItem {
    public NoValue() {
        super("NOVALUE");

    }


    public static void main(String[] args) {
        ColumnItem x = new NoValue();
        System.out.println(x.getClass());


    }

}


