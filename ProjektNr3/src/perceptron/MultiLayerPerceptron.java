package perceptron;

import java.util.Arrays;

public class MultiLayerPerceptron {

	private Perceptron[][] perceptronNetwork;
	private double[][] delta;
	private int numberOfLayers;
	private int numberOfInputs;     //ilość sygnałów wejściowych dla pierwszej warstwy
	private int[] layersSize;       //tablica przechowująca ilośc perceptronów w każdej warstwie
	private double learningRate;

	public MultiLayerPerceptron ( int numberOfLayers, int[] layersSize, int numberOfInputs, double learningRate ) {
		this.numberOfLayers = numberOfLayers;
		this.numberOfInputs = numberOfInputs + 1; //zwiększam o 1 bo jeszcze bias
		this.layersSize = layersSize;
		this.learningRate = learningRate;

		//tworzenie perceptronów w sieci
		perceptronNetwork = new Perceptron[numberOfLayers][];

		for ( int i = 0; i < numberOfLayers; i++ )
			perceptronNetwork[i] = new Perceptron[layersSize[i]];

		for ( int i = 0; i < layersSize[0]; i++ )
			perceptronNetwork[0][i] = new Perceptron( this.numberOfInputs );    //ilość wejść dla pierwszej warstwy sieci

		for ( int i = 1; i < numberOfLayers; i++ )
			for ( int j = 0; j < layersSize[i]; j++ )
				perceptronNetwork[i][j] = new Perceptron( layersSize[i - 1] + 1 );    //ilość wejść do neuronu to ilość neuronów w warstwie poprzedniej


		//tworzenie tablicy przechowującą wartości błędów, w celu wykonania wstecznej propagacji
		delta = new double[numberOfLayers][];

		for ( int i = 0; i < numberOfLayers; i++ )
			for ( int j = 0; j < layersSize[i]; j++ )
				delta[i] = new double[layersSize[i]];

		for ( int i = 0; i < numberOfLayers; i++ )
			Arrays.fill( delta[i], 0 );
	}

	public void Learn ( double[] x, double y ) {
		double[][] vectors = new double[numberOfLayers + 1][];    //służy do przekazywania dane z jednej warstwy do następnej

		vectors[0] = new double[numberOfInputs];                //pierwszy wymiar to dane wejściowe do nauki

		//kolejne dane to dane z perceptronów z poprzedniej warstwy
		for ( int i = 1; i < numberOfLayers + 1; i++ )
			vectors[i] = new double[layersSize[i - 1] + 1];

		//wypełnienie biasami
		for ( int i = 0; i < numberOfLayers + 1; i++ )
			vectors[i][0] = 1;

		System.arraycopy( x, 0, vectors[0], 1, numberOfInputs - 1 ); //przerzucenie wartosci z tablicy x do pierwszej warstwy vectora

		System.out.println( "UCZENIE I FAZA - PRZEPUSZCZENIE INPUTU W CELU OKREŚLENIA SYGNAŁU WYJŚCIOWEGO\n" );
		System.out.println( Arrays.toString( vectors[0] ) );

		//każda warstwa dostaje do nauki vektor, który skłąda się z tego co wyszło z warstwy poprzedniej
		//dla warstwy pierwszej tym wektorem są dane wejściowe
		for ( int i = 0; i < numberOfLayers; i++ ) {
			for ( int j = 0; j < layersSize[i]; j++ )
				vectors[i + 1][j + 1] = perceptronNetwork[i][j].test( vectors[i] );
			System.out.println( Arrays.toString( vectors[i + 1] ) );
		}


		System.out.println( "\n\n\nUCZENIE II FAZA - LICZENIE DELT\n" );

		//zaczynamy od tyłu
		//najpierw określamy wartość błędu jako wartość oczekiwana - wartość otrzymana
		for ( int i = 0; i < layersSize[numberOfLayers - 1]; i++ )
			delta[numberOfLayers - 1][i] = y - vectors[numberOfLayers][i + 1];

		for ( int i = numberOfLayers - 2; i >=0; i-- )
			for ( int j = 0; j < layersSize[i]; j++ )
				for ( int k = 0; k < layersSize[i+1]; k++ )
					delta[i][j] += delta[i+1][k] * perceptronNetwork[i+1][k].getW( j+1 );

		for ( int i = 0; i < numberOfLayers; i++ )
			System.out.println( Arrays.toString( delta[i] ) );



		System.out.println( "\n\n\nUCZENIE III FAZA - MODYFIKACJA WAG\n" );
		System.out.println( Arrays.toString( vectors[0] ) );

		//każda warstwa dostaje do nauki vektor, który skłąda się z tego co wyszło z warstwy poprzedniej
		//dla warstwy pierwszej tym wektorem są dane wejściowe
		for ( int i = 0; i < numberOfLayers; i++ ) {
			for ( int j = 0; j < layersSize[i]; j++ )
				vectors[i + 1][j + 1] = perceptronNetwork[i][j].backlearn( vectors[i], y, learningRate );
			System.out.println( Arrays.toString( vectors[i + 1] ) );
		}

	}
}
