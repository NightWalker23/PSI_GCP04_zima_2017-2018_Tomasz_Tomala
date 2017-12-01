package rastrigin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class Rastrigin {
	private static int trainingSize = 3500, testingSize = 1500; //rozmiar danych uczących i testujących
	static double[][] trainingData;
	static double[][] testingData;
	static double[] trainingY;
	static double[] testingY;

	public static void main ( String[] args ) throws FileNotFoundException {
		generateData();

		PrintWriter pw_train = new PrintWriter(new File("train.csv"));
		PrintWriter pw_test = new PrintWriter(new File("test.csv"));
		StringBuilder sb_train = new StringBuilder();
		StringBuilder sb_test = new StringBuilder();

		for ( int i = 0; i < trainingSize; i++ ) {
			sb_train.append( trainingData[i][0] );
			sb_train.append( ',' );
			sb_train.append( trainingData[i][1] );
			sb_train.append( ',' );
			sb_train.append( trainingY[i] );
			sb_train.append( '\n' );
		}

		for ( int i = 0; i < testingSize; i++ ){
			sb_test.append(testingData[i][0]);
			sb_test.append(',');
			sb_test.append(testingData[i][1]);
			sb_test.append(',');
			sb_test.append(testingY[i]);
			sb_test.append('\n');
		}

		pw_train.write(sb_train.toString());
		pw_train.close();

		pw_test.write(sb_test.toString());
		pw_test.close();
	}

	private static double calculateRastrigin3D ( double x, double y ) {
		return 20 + Math.pow( x, 2 ) + Math.pow( y, 2 ) - 10 * ( Math.cos( 2 * Math.PI * x ) + Math.cos( 2 * Math.PI * y ) );
	}

	//generowanie danych uczących i testujących
	private static void generateData () {
		trainingData = new double[trainingSize][2];
		testingData = new double[testingSize][2];
		trainingY = new double[trainingSize];
		testingY = new double[testingSize];

		Random rand = new Random();

		for ( int i = 0; i < trainingSize; i++ ) {
			trainingData[i][0] = normalization( 4.0 * rand.nextDouble() - 2.0 );
			trainingData[i][1] = normalization( 4.0 * rand.nextDouble() - 2.0 );
			trainingY[i] = normalizationRastrigin( calculateRastrigin3D( trainingData[i][0], trainingData[i][1] ) );
		}

		for ( int i = 0; i < testingSize; i++ ) {
			testingData[i][0] = normalization( 4.0 * rand.nextDouble() - 2.0 );
			testingData[i][1] = normalization( 4.0 * rand.nextDouble() - 2.0 );
			testingY[i] = normalizationRastrigin( calculateRastrigin3D( testingData[i][0], testingData[i][1] ) );
		}
	}

	//funkcja normalizująca dane "z" funkcji Rastrigin
	private static double normalizationRastrigin ( double x ) {
		double min = 0, max = 45;
		double new_min = 0, new_max = 1;

		return ( ( x - min ) / ( max - min ) ) * ( new_max - new_min ) + new_min;
	}

	//funkcja normalizująca dane wejściowe "x" i "y" do funkcji Rastrigin
	private static double normalization ( double x ) {
		double min = - 2, max = 2;
		double new_min = 0, new_max = 1;

		return ( ( x - min ) / ( max - min ) ) * ( new_max - new_min ) + new_min;
	}
}
