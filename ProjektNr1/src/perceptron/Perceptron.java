package perceptron;

public class Perceptron {

    //wagi
    public double w0 = 0.5, w1 = 0.5, w2 = 0.5;

    //funkcja aktywująca
    public int active ( double y_p ) {
        if ( y_p < 0 ) return 0;
        else return 1;
    }

    //sumator
    public int process ( int x0, int x1, int x2 ) {
        double y_p = x0 * w0 + x1 * w1 + x2 * w2;
        return active( y_p );
    }

    //uczenie
    public void learn ( int x0, int x1, int x2, double y, double lr ) {
        double y_p = process( x0, x1, x2 );
        w0 += ( y - y_p ) * lr * x0;
        w1 += ( y - y_p ) * lr * x1;
        w2 += ( y - y_p ) * lr * x2;
    }

    //wyświetlenie wag
    public void showW(){
        System.out.println("WAGI:");
        System.out.println( "w0 = " + w0 );
        System.out.println( "w1 = " + w1 );
        System.out.println( "w2 = " + w2 );
        System.out.println();
    }
}