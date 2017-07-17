package db;

/**
 * Created by joshgoldman on 2/25/17.
 */
public interface Subtractor<Type> {
    ColumnItem subtractItems(ColumnItem first, ColumnItem second);

}
