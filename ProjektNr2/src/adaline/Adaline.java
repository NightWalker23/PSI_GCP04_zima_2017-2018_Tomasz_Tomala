package adaline;

import java.util.Random;

public class Adaline {

    private int noi; //ilość wejść
    private double[] w; //wagi

    public Adaline ( int numbers_of_inputs ) {
        noi = numbers_of_inputs;
        w = new double[noi];

        for ( int i = 0; i < noi; i++ )
            w[i] = new Random().nextDouble(); //wagi początkowe sa losowane
    }

    //funkcja aktywująca
    private int active ( double y_p ) {
        return y_p <= 0 ? (-1) : 1;
    }

    //sumator
    public double process ( int[] x ) {
        double y_p = 0;
        for ( int i = 0; i < noi; i++ )
            y_p += x[i] * w[i];

        return y_p;
    }

    //uczenie
    public void learn ( int[] x, double y, double lr ) {
        double y_p = process( x );

        for ( int i = 0; i < noi; i++ )
            w[i] += ( y - y_p ) * lr * x[i];
    }

    //testowanie
    public int test ( int[] x )
    {
        return ( active( process( x ) ) );
    }
}