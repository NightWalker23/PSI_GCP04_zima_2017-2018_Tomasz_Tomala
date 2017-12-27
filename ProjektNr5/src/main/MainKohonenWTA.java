package main;


public class MainKohonenWTA {

	private static double learningRate = 0.0001;		//współczynnik uczenia się
	private static int numberOfInputs = 4;				//ilość wejść
	private static int numberOfNeurons = 200;			//liczba neuronów
	private static int numberOfFlowers = 3;				//liczba kwiatów
	private static int numberOfLearnSamples = 15;		//liczba danych uczących dla każdego kwiatu
	private static int numberOfTestSamples = 6;			//liczba danych testujacych dla każdego kwiatu

	public static void main ( String[] args ) {

		KohonenWTA[] kohonens = new KohonenWTA[numberOfNeurons];
		for ( int i = 0; i < numberOfNeurons; i++ )
			kohonens[i] = new KohonenWTA( numberOfInputs );

		int ages = learn( kohonens );
		int winner;

		System.out.println( "PO UCZENIU" );
		for ( int i = 0; i < numberOfFlowers; i++ ) {
			for ( int j = 0; j < numberOfLearnSamples; j++ ) {
				winner = getWinner( kohonens, Flower.flowerLearn[i][j] );
				System.out.println( "Flower[" + i + "][" + j + "] winner = " + winner );
			}
			System.out.println();
		}
		System.out.println();


		System.out.println( "PO TESTOWANIU" );
		for ( int i = 0; i < numberOfFlowers; i++ ) {
			for ( int j = 0; j < numberOfTestSamples; j++ ) {
				winner = getWinner( kohonens, Flower.flowerTest[i][j] );
				System.out.println( "Flower[" + i + "][" + j + "] test winner = " + winner );
			}
			System.out.println();
		}
		System.out.println();


		System.out.println( "Ilość epok = " + ages );
	}


	private static int learn ( KohonenWTA[] kohonens ) {

		int counter = 0;
		int limit = 10000;
		int winner;

		int[][] winners = new int[numberOfFlowers][numberOfLearnSamples];
		for ( int i = 0; i < numberOfFlowers; i++ )
			for ( int j = 0; j < numberOfLearnSamples; j++ )
				winners[i][j] = - 1;

		while ( ! isUnique( winners ) ) {

			for ( int i = 0; i < numberOfFlowers; i++ ) {
				for ( int j = 0; j < numberOfLearnSamples; j++ ) {
					winner = getWinner( kohonens, Flower.flowerLearn[i][j] );
					kohonens[winner].learn( Flower.flowerLearn[i][j], learningRate );
				}
			}

			for ( int i = 0; i < numberOfFlowers; i++ )
				for ( int j = 0; j < numberOfLearnSamples; j++ )
					winners[i][j] = getWinner( kohonens, Flower.flowerLearn[i][j] );

			if ( ++ counter == limit )
				break;
		}

		return counter;
	}

	private static boolean isUnique ( int[][] winners ) {

		//czy kwiaty danego gatunku mają tylko jednego zwycięzcę
		for ( int i = 0; i < numberOfFlowers; i++ )
			for ( int j = 1; j < numberOfLearnSamples; j++ )
				if ( winners[i][0] != winners[i][j] )
					return false;

		//czy zwycięzca każdego z gatunków różni się od zwycięzców pozostałych gatunków
		for ( int i = 0; i < numberOfFlowers; i++ )
			for ( int j = 0; j < numberOfFlowers; j++ )
				if ( i != j )
					if ( winners[i][0] == winners[j][0] )
						return false;

		return true;
	}

	private static int getWinner ( KohonenWTA[] kohonens, double[] vector ) {

		int winner = 0;
		double minDistance = distanceBetweenVectors( kohonens[0].getW(), vector );

		for ( int i = 0; i < numberOfNeurons; i++ ) {
			if ( distanceBetweenVectors( kohonens[i].getW(), vector ) < minDistance ) {
				winner = i;
				minDistance = distanceBetweenVectors( kohonens[i].getW(), vector );
			}
		}

		return winner;
	}

	public static double distanceBetweenVectors ( double[] vector1, double[] vector2 ) {

		double suma = 0.0;

		for ( int i = 0; i < vector1.length; i++ ) {
			//suma += Math.pow( vector1[i] - vector2[i], 2 ); //miara Euklidesowa
			suma += Math.abs( vector1[i] - vector2[i] ); //miara Manhattan
		}

		return Math.sqrt( suma );
	}

	public static void showDistance ( KohonenWTA[] kohonenWTAS, double[] vector, int x, int y ) {

		double distance;
		for ( int i = 0; i < numberOfNeurons; i++ ) {
			distance = distanceBetweenVectors( kohonenWTAS[i].getW(), vector );
			System.out.println( "Neuron[" + i + "] distance to flower <" + x + ", " + y + "> = " + distance );
		}
	}

}
