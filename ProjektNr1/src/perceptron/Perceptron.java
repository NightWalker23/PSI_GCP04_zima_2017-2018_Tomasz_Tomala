package perceptron;

public class Perceptron {

    private int noi; //ilość wejść
    private double[] w; //wagi

    public Perceptron ( int numbers_of_inputs ) {
        noi = numbers_of_inputs;
        w = new double[noi];

        for ( int i = 0; i < noi; i++ )
            w[i] = 0.5;
    }

    //funkcja aktywująca
    private int active ( double y_p ) {
        return y_p < 0 ? 0 : 1;
    }

    //sumator
    public int process ( int[] x ) {
        double y_p = 0;
        for ( int i = 0; i < noi; i++ )
            y_p += x[i] * w[i];

        return active( y_p );
    }

    //uczenie
    public void learn ( int[] x, double y, double lr ) {
        double y_p = process( x );

        for ( int i = 0; i < noi; i++ )
            w[i] += ( y - y_p ) * lr * x[i];
    }

    public double getW ( int i ) {
        return w[i];
    }
}