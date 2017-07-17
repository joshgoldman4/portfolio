package db;

/**
 * Created by joshgoldman on 2/25/17.
 */
public interface Divider<Type> {
    ColumnItem divideItems(ColumnItem first, ColumnItem second);

}
