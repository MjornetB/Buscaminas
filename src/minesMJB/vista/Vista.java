package minesMJB.vista;

/**
 * aquesta classe serveix per mostrarli a l'usuari els missatges corresponents.
 */
public class Vista {




    /**
     * Mostra el camp per consola
     *
     * @param campMines el camp dessitjat per mostrar
     */
    public static void mostrarCamp(char[][] campMines) {
        char caracterLat = '@';
        char caracterSup = '0';
        int[] numerosArriba = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.print("  ");
        for (int i = 0; i < campMines.length - 2; i++) {
            System.out.print(numerosArriba[i] + " ");
        }
        System.out.println(" ");
        for (int f = 1; f < campMines.length - 1; f++) {
            System.out.print((char) ('@' + f) + " ");
            for (int c = 1; c < campMines[0].length - 1; c++) {

                System.out.print(campMines[f][c] + " ");
            }
            System.out.println();
        }
    }





    /**
     * funcio que mostra per consola el missatge a l'usuari
     * @param mensaje que es printara
     */
    public static void missatgeJunt(String mensaje) {
        System.out.print(mensaje);
    }






    /**
     * fa lo mateix que la funcio missatgeJunt pero en comptes de treballar amb un print, ho fa amb un println
     * @param mensaje que se printara
     */
    public static void missatge(String mensaje) {
        System.out.println(mensaje);
    }



}
