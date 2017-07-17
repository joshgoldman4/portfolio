import java.lang.reflect.Type;

/**
 * Created by joshgoldman on 2/18/17.
 */
public class noValue<Type> extends columnItem<Type> {


    noValue(Type x){
        super(x);




    }

    @Override
    public void print(){
        System.out.println("NOVALUE");

    }
}
