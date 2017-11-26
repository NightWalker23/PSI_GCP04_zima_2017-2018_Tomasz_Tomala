package perceptron;

public class Main_perceptron {

    public static void main ( String[] args ) {

        int numberOfInputs = 2;         //ilość wejść
        int counter = 0;                //licznik ilości epok uczenia się
        double learningRate = 0.1;      //krok uczenia się

        MultiLayerPerceptron MLP = new MultiLayerPerceptron( 3, new int[]{1, 3, 1}, numberOfInputs, learningRate );
        MLP.Learn( new double[]{1, 0}, 1.0 );


    }

}