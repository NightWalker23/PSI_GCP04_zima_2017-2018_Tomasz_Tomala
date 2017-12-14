package main;

public class Main {

	static int numberOfInputs = 64 + 1;					//ilość wejść (+1 bo bias)
	static double learningRate = 0.01;					//współczynnik uczenia się
	static double forgettingRate = learningRate / 6.0;	//współczynnik zapominania
	static int numberOfEmoji = 4;						//liczba emotikonów
	static int numberOfNeurons = 5;						//liczba neuronów

	public static void main ( String[] args ) {

		int winner;
		Hebb[] hebbs = new Hebb[numberOfNeurons];
		for ( int i = 0; i < numberOfNeurons; i++ )
			hebbs[i] = new Hebb( numberOfInputs );

		int ages = learn( hebbs );

		System.out.println( "PO UCZENIU" );
		for ( int i = 0; i < numberOfEmoji; i++ ) {
			winner = testHebb( hebbs, Emoji.emoji[i] );
			System.out.println( "Emoji " + Emoji.emojiType[i] + " - winner neuron = " + winner );
		}

		System.out.println( "\nTESTOWANIE" );
		for ( int i = 0; i < numberOfEmoji; i++ ) {
			winner = testHebb( hebbs, Emoji.emojiNoised[i] );
			System.out.println( "Emoji " + Emoji.emojiType[i] + " - winner neuron = " + winner );
		}

		System.out.println( "\nIlość epok = " + ages );

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
					hebbs[j].learnUnsupervised( Emoji.emoji[k], learningRate, forgettingRate, Hebb.HEBB_WITH_FORGETTING );

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