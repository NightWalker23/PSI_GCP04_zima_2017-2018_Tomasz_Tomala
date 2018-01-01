package main;

public class MainKohonenWTM {

	private static double learningRate = 0.1;			//współczynnik uczenia się
	private static int numberOfInputs = 70;				//ilość wejść
	private static int numberOfNeurons = 500;			//liczba neuronów
	private static int numberOfLearnSamples = 20;		//liczba danych uczących dla każdego kwiatu
	private static int numberOfTestSamples = 20;		//liczba danych testujacych dla każdego kwiatu
	private static int learnLimit = 50;					//maksymalny próg epok uczenia
	private static double neighborhoodRadius = 5.0;		//początkowa wartość promienia sąsiedztwa

	public static void main ( String[] args ) {

		KohonenWTM[] kohonens = new KohonenWTM[numberOfNeurons];
		for ( int i = 0; i < numberOfNeurons; i++ )
			kohonens[i] = new KohonenWTM( numberOfInputs );

		int ages = learn( kohonens );

		int[] winnerLearn = new int[numberOfLearnSamples], winnerTest = new int[numberOfTestSamples];
		int percent = 0;

		//wyniki uczenia
		for ( int i = 0; i < numberOfLearnSamples; i++ )
			winnerLearn[i] = getWinner( kohonens, Letters.lettersLearn[i] );

		//wyniki testowania
		for ( int i = 0; i < numberOfLearnSamples; i++ )
			winnerTest[i] = getWinner( kohonens, Letters.lettersTest[i] );

		for ( int i = 0; i < numberOfTestSamples; i++ )
			if ( winnerLearn[i] == winnerTest[i] )
				percent++;

		System.out.println( "Ilość epok = " + ages );
		System.out.println( "Poprawność testowania = " + ( ( percent * 100 ) / numberOfTestSamples ) + "%" );
	}


	//uczenie sieci
	private static int learn ( KohonenWTM[] kohonens ) {

		int counter = 0;
		int winner;

		int[] winners = new int[numberOfLearnSamples];
		for ( int i = 0; i < numberOfLearnSamples; i++ )
			winners[i] = - 1;

		while ( ! isUnique( winners ) ) {    //dopóki sieć się nauczy

			//uczymy sieć po kolei każdy kwiat z każdego gatunku
			for ( int i = 0; i < numberOfLearnSamples; i++ ) {
				winner = getWinner( kohonens, Letters.lettersLearn[i] );
				kohonens[winner].learn( Letters.lettersLearn[i], learningRate );

				//uczenie sąsiednich neuronów
				for ( int j = 0; j < numberOfNeurons; j++ )
					if ( j != winner )
						if ( distanceBetweenVectors( kohonens[winner].getW(), kohonens[j].getW() ) <= neighborhoodRadius )
							kohonens[j].learn( Letters.lettersLearn[i], learningRate );
			}

			//po zakończeniu epoki pobieramy zwycięzców
			for ( int i = 0; i < numberOfLearnSamples; i++ )
				winners[i] = getWinner( kohonens, Letters.lettersLearn[i] );

			//jeśli ilość prób nauczenia osiągnie limit to uczenie uznajemy za nieudane i kończymy
			if ( ++ counter == learnLimit )
				break;
		}

		return counter;
	}


	//sprawdza czy sieć jest już nauczona
	private static boolean isUnique ( int[] winners ) {

		//czy zwycięzca każdego z gatunków różni się od zwycięzców pozostałych gatunków
		for ( int i = 0; i < numberOfLearnSamples; i++ )
			for ( int j = i; j < numberOfLearnSamples; j++ )
				if ( i != j )
					if ( winners[i] == winners[j] )
						return false;

		return true;
	}


	//zwraca zwycięzcę dla danego kwiatu
	private static int getWinner ( KohonenWTM[] kohonens, double[] vector ) {

		int winner = 0;
		double minDistance = distanceBetweenVectors( kohonens[0].getW(), vector );

		//sprawdza który neuron jest zwycięzcą
		//miarą zwycięztwa jest odległość między wektorem wag neuronu a wektorem cech kwiatu
		for ( int i = 0; i < numberOfNeurons; i++ ) {
			if ( distanceBetweenVectors( kohonens[i].getW(), vector ) < minDistance ) {
				winner = i;
				minDistance = distanceBetweenVectors( kohonens[i].getW(), vector );
			}
		}

		return winner;
	}


	//zwraca odległość między zadanymi wektorami
	private static double distanceBetweenVectors ( double[] vector1, double[] vector2 ) {

		double suma = 0.0;

		for ( int i = 0; i < vector1.length; i++ )
			suma += Math.abs( vector1[i] - vector2[i] ); //miara Manhattan

		return Math.sqrt( suma );
	}

}