package main;

import java.util.Random;

public class KohonenWTM {

	private int noi;		//ilość wejść
	private double[] w;		//wagi

	public KohonenWTM ( int numbers_of_inputs ) {
		noi = numbers_of_inputs;
		w = new double[noi];

		for ( int i = 0; i < noi; i++ )
			w[i] = new Random().nextDouble();	//wagi początkowe sa losowane w zakresie od 0 do 1
	}

	//uczenie poprzez zmniejszenie odległości między wektorem wag a zadanym wektorem
	public void learn ( double[] x, double lr ) {

		for ( int i = 0; i < noi; i++ )
			w[i] += lr * ( x[i] - w[i] );
	}

	//zwraca wektor wag
	public double[] getW () {
		return w;
	}
}