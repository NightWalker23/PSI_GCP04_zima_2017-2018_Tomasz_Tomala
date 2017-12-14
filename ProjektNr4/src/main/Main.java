package main;

public class Main {

	static int numberOfInputs = 64 + 1;					//ilość wejść
	static double learningRate = 0.01;					//współczynnik uczenia się
	static double forgettingRate = learningRate / 3.0;	//współczynnik zapominania
	static int numberOfEmoji = 4;						//liczba emotikonów
	static int numberOfNeurons = 5;						//liczba nauronów

	public static void main ( String[] args ) {

		int winner;
		Hebb[] hebbs = new Hebb[numberOfNeurons];
		for ( int i = 0; i < numberOfNeurons; i++ )
			hebbs[i] = new Hebb( numberOfInputs );

		System.out.println( "PRZED UCZENIEM" );
		for ( int i = 0; i < numberOfEmoji; i++ ) {
			winner = testHebb( hebbs, Emoji.emoji[i] );
			System.out.println( "Winner Hebb = " + winner );
		}

		int ages = learn( hebbs );

		System.out.println( "\n\nPO UCZENIU" );
		for ( int i = 0; i < numberOfEmoji; i++ ) {
			winner = testHebb( hebbs, Emoji.emoji[i] );
			System.out.println( "Winner Hebb = " + winner );
		}

		System.out.println( "\n\nIlość epok = " + ages );

		System.out.println( "\n\nTESTOWANIE" );
		for ( int i = 0; i < numberOfEmoji; i++ ) {
			winner = testHebb( hebbs, Emoji.emojiNoised[i] );
			System.out.println( "Winner Hebb = " + winner );
		}

	}


	//uczenie neuronów
	public static int learn ( Hebb[] hebbs ) {

		int counter = 0;
		int limit = 1000;
		int[] winners = new int[numberOfNeurons];
		for ( int i = 0; i < numberOfNeurons; i++ )
			winners[i] = - 1;

		while ( ! isUnique( winners ) ) {

			for ( int j = 0; j < numberOfNeurons; j++ ) {

				//uczenie neuronów każdej emotikony
				for ( int k = 0; k < numberOfEmoji; k++ )
					hebbs[j].learnUnsupervised( Emoji.emoji[k], learningRate, forgettingRate, Hebb.HEBB_WITHOUT_FORGETTIN );

				//tesotowanie sieci celem sprawdzenia, czy sieć jest już nauczona
				for ( int l = 0; l < numberOfEmoji; l++ )
					winners[l] = testHebb( hebbs, Emoji.emoji[l] );
			}

			if ( ++ counter == limit )
				break;
		}

		return counter;
	}

	//funkcja pomocnicza w procesie uczenie
	//zwraca true jeśli każdy element w tablicy jest unikalny
	public static boolean isUnique ( int[] winners ) {

		for ( int i = 0; i < numberOfNeurons; i++ )
			for ( int j = 0; j < numberOfNeurons; j++ )
				if ( i != j )
					if ( winners[i] == winners[j] )
						return false;

		return true;
	}

	//zwraca wartość zwycięzkiego neuronu dla podanej emotikony
	public static int testHebb ( Hebb[] hebbs, double[] emoji ) {

		double max = hebbs[0].test( emoji );
		int winner = 0;

		for ( int i = 1; i < numberOfNeurons; i++ ) {
			if ( hebbs[i].test( emoji ) > max ) {
				max = hebbs[i].test( emoji );
				winner = i;
			}
		}

		return winner;
	}

}