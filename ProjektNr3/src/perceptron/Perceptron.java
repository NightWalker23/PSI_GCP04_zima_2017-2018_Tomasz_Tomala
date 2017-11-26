package perceptron;

import java.util.Random;

public class Perceptron {

	private int noi; //ilość wejść
	private double[] w; //wagi

	public Perceptron ( int numbers_of_inputs ) {
		noi = numbers_of_inputs;
		w = new double[noi];

		for ( int i = 0; i < noi; i++ )
			w[i] = new Random().nextDouble(); //wagi początkowe sa losowane
	}

	//funkcja aktywacji typu sigmoidalnego bipolarnego, zwraca wartości od -1 do 1
	private double active ( double y_p ) {
		return Math.tanh( y_p );
	}

	//pochodna funkcji aktywacji
	private double derivative ( double y_p ) {
		return ( 1 - Math.pow( Math.tanh( y_p ), 2 ) );
	}

	//sumator
	private double process ( double[] x ) {
		double y_p = 0;
		for ( int i = 0; i < noi; i++ )
			y_p += x[i] * w[i];

		return active( y_p );
	}

	//uczenie
	public double learn ( double[] x, double y, double lr ) {
		double y_p = process( x );

		for ( int i = 0; i < noi; i++ )
			w[i] += ( y - y_p ) * lr * x[i]; //modyfikacja wag

		return test( x );
	}

	//uczenie z wsteczną propagacją błędu
	public double backlearn ( double[] x, double delta, double lr ) {
		double y_p = process( x );

		for ( int i = 0; i < noi; i++ )
			w[i] += lr * delta * derivative( y_p ) * x[i];

		return test( x );
	}

	//testuje perceptron
	public double test ( double[] x ) {
		return active( process( x ) );
	}

	public double getW ( int i ) {
		return w[i];
	}
}