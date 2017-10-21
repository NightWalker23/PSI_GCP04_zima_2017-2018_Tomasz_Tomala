package perceptron;

import java.util.Arrays;

public class Main {

    public static void main ( String[] args ) {

        int number_of_inputs = 3;
        Perceptron perc = new Perceptron( number_of_inputs );

        int n = 0; //licznik ilości epok uczenia się
        int r = 4; //rozmiar tablic danych wejściowych
        double learning_rate = 0.01; //krok uczenia się

        int x0 = 1; //bias

        //dane wejściowe do AND
        int[] x1 = { 0, 0, 1, 1 };
        int[] x2 = { 0, 1, 0, 1 };

        //dane oczekiwane
        int[] y = { 0, 0, 0, 1 }; //AND
        int[] wyj = new int[4]; //tablica przechowująca wyniki testowania perceptronu

        //uczenie perceptronu
        while ( ! Arrays.equals( y, wyj ) ) {
            for ( int i = 0; i < r; i++ )
                perc.learn( new int[] { x0, x1[i], x2[i] }, y[i], learning_rate );

            for ( int i = 0; i < r; i++ )
                wyj[i] = perc.process( new int[] { x0, x1[i], x2[i] } );

            n++;
        }

        System.out.println( "Ilość kroków do nauczenia się = " + n );
    }
}