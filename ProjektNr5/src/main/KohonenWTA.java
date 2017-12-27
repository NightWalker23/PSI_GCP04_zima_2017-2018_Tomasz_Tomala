package main;

import java.util.Random;

public class KohonenWTA {

	private int noi;		//ilość wejść
	private double[] w;		//wagi

	public KohonenWTA ( int numbers_of_inputs ) {
		noi = numbers_of_inputs;
		w = new double[noi];

		for ( int i = 0; i < noi; i++ )
			w[i] = new Random().nextDouble();	//wagi początkowe sa losowane

		//normalizeWeights();
	}

	//uczenie
	public void learn ( double[] x, double lr ) {

		for ( int i = 0; i < noi; i++ )
			w[i] += lr * ( x[i] - w[i] );

		//normalizeWeights();
	}

	//normalizuje wagi
	private void normalizeWeights () {

		double dl = 0.0;
		for ( int i = 0; i < w.length; i++ )
			dl += Math.pow( w[i], 2 );

		dl = Math.sqrt( dl );

		for ( int i = 0; i < w.length; i++ )
			if ( w[i] > 0 && dl != 0 )
				w[i] = w[i] / dl;
	}

	public double[] getW () {
		return w;
	}
}