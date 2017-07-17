package db;

import java.util.Comparator;

/**
 * Created by joshgoldman on 2/25/17.
 */
public interface Adder<Type> {
    ColumnItem addItems(ColumnItem first, ColumnItem second);

}
