package db;

import java.util.ArrayList;

/**
 * Created by joshgoldman on 2/24/17.
 */
public class Column extends ArrayDeque<ColumnItem> {
    public String columnName;

    public Column(String input) {
        super();
        columnName = input;
    }


    public Object findItem(int index) {
        ColumnItem item = super.get(index);
        return item.input;


    }


}
