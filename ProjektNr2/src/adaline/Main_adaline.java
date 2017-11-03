package adaline;

import alphabet.Alphabet;

import java.util.Arrays;

public class Main_adaline {

    public static void main ( String[] args ) {

        int noi = 7;        //ilość wejść
        int nol = 26;       //ilość liter MAX 26
        int counter = 0;    //licznik ilości epok uczenia się
        double lr = 0.01;   //krok uczenia się

        Adaline[] ada = new Adaline[noi];
        for ( int i = 0; i < noi; i++ )
            ada[i] = new Adaline( noi );

        int[] y = new int[nol * 2];     //0 - duża litera, 1 - mała litera
        Arrays.fill( y, 0, nol, - 1 );
        Arrays.fill( y, nol, nol * 2, 1 );

        int[] wyj = new int[nol * 2];   //tablica przechowująca wyniki testowania adaline
        Arrays.fill( wyj, 0, nol * 2, 0 );

        int proc;   //zmienna pomocnicza do sprawozdania

        while ( ! Arrays.equals( y, wyj ) ) {
            proc = 0;

            for ( int i = 0; i < 2; i++ ) {     //-1 - wielkie litery, 1 - małe litery
                for ( int j = 0; j < nol; j++ )
                    learn( ada, noi, lr, i, j );
            }

            wyj = test( ada, nol, noi );

            for ( int i = 0; i < nol * 2; i++ ) //testowanie, do sprawozdania
                if ( wyj[i] != y[i] )
                    proc++;

            counter++;
            System.out.format( "%.6f%n", ( double ) proc / ( nol * 2 ) );
        }
        System.out.println();


        //TESTOWANIE
        for ( int i = 0; i < nol; i++ )     //wyświetlenie liter
            System.out.print( ( char ) ( i + 65 ) + "\t" );
        System.out.println();

        for ( int i = 0; i < 2; i++ ) {     //wyświetlenie wynków
            for ( int j = 0; j < nol; j++ )
                System.out.print( wyj[i * nol + j] + "\t" );
            System.out.println();
        }

        System.out.println( "\nIlość kroków do nauczenia się = " + counter );
    }


    private static void learn ( Adaline[] ada, int noi, double lr, int i, int j ) {
        int[] vector;                   //tablica przechowująca wektor sygnałów wejściowych do uczenia pierwszej warstwy sieci
        vector = Alphabet.getLetter( i, j );
        format( vector );

        int[] vector_p = new int[noi];  //tablica przechowująca wektor sygnałów wyjściowych pierwszej warstwy sieci
        vector_p[0] = 1; //bias

        int letter_size;
        if ( i == 0 ) letter_size = - 1;
            else letter_size = 1;

        for ( int k = 0; k < noi - 1; k++ ) {               //uczenie pierwszej warstwy
            ada[k].learn( vector, letter_size, lr );
            vector_p[k + 1] = ada[k].test( vector );        //pobranie sygnału wyjściowego
        }
        ada[noi - 1].learn( vector_p, letter_size, lr );    //uczenie perceptronu wynikowego na podstawie sygnałów wyjściowych pierwszej warstwy
    }

    private static int[] test ( Adaline[] ada, int nol, int noi ) {
        int[] wyj = new int[nol * 2];
        int[] vector;                   //tablica przechowująca wektor sygnałów wejściowych do testowania pierwszej warstwy sieci
        int[] vector_p = new int[noi];  //tablica przechowująca wektor sygnałów wyjściowych pierwszej warstwy sieci
        vector_p[0] = 1;                //bias

        for ( int i = 0; i < 2; i++ ) { //testowanie, celem upewnienia się, czy sieć już nauczona
            for ( int j = 0; j < nol; j++ ) {
                vector = Alphabet.getLetter( i, j );
                format( vector );

                for ( int k = 0; k < noi - 1; k++ )
                    vector_p[k + 1] = ada[k].test( vector );

                wyj[i * nol + j] = ada[noi - 1].test( vector_p );
            }
        }
        return wyj;
    }

    //w przypadku adaline sygnały wejściowe = 0 muszą być zamienione na sygnały -1
    private static void format( int[] vector ){
        for ( int k = 0; k < vector.length; k++ )
            if ( vector[k] == 0 ) vector[k] = -1;
    }
}