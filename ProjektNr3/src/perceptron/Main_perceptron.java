package perceptron;

public class Main_perceptron {

	public static void main ( String[] args ) {

		int numberOfInputs = 2;         //ilość wejść
		int counter = 0;                //licznik ilości epok uczenia się
		double learningRate = 0.1;      //krok uczenia się

		MultiLayerPerceptron MLP = new MultiLayerPerceptron( 3, new int[] { 1, 3, 1 }, numberOfInputs, learningRate );
		MLP.BackPropagationLearn( new double[] { 1, 0 }, 1.0 );


		//co do Rastrigin 3D
		//https://www.mathworks.com/help/gads/example-rastrigins-function.html?requestedDomain=www.mathworks.com
		//20+ x^2 + y^2 − 10( cos(2*π*x) +cos(2*π*y) )

	}

}