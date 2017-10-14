package perceptron;

public class Main {

    public static void main ( String[] args ) {

        Perceptron perc = new Perceptron();

        int n = 3; //ilość powtórzeń uczenia się
        int r = 4; //rozmiar tablic danych wejściowych
        double learning_rate = 0.1; //krok uczenia się

        int x0 = 1; //bias

        //dane wejściowe do AND i OR
        int[] x1 = { 0, 0, 1, 1 };
        int[] x2 = { 0, 1, 0, 1 };

        //dane oczekiwane
        int[] y = { 0, 0, 0, 1 }; //AND
        //int[] y = { 0, 1, 1, 1 }; //OR

        //uczenie perceptronu
        for ( int j = 0; j < n; j++ ) {
            for ( int i = 0; i < r; i++ ) {
                perc.learn( x0, x1[i], x2[i], y[i], learning_rate );
            }
        }

        perc.showW(); //wyświetlenie wag

        //testowanie perceptronu
        System.out.println( perc.process( x0, 0, 0 ) );
        System.out.println( perc.process( x0, 0, 1 ) );
        System.out.println( perc.process( x0, 1, 0 ) );
        System.out.println( perc.process( x0, 1, 1 ) );
    }
}