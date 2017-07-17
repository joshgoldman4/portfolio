package db;

/**
 * Created by joshgoldman on 2/25/17.
 */
public interface Multiplier<Type> {

    ColumnItem multiplyItems(ColumnItem first, ColumnItem second);

}
