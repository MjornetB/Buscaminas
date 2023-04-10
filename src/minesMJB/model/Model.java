package minesMJB.model;

import minesMJB.vista.Vista;

import java.util.Random;

import static minesMJB.vista.Vista.mostrarCamp;

/**
 * Aquesta classe es basa en tot el process intern que realitza el programa.
 */
public class Model {

    static int nFiles;
    static int nColumnes;
    static int nBombes;
    static boolean jocFinalitzat;
    static char[][] jocOcult;
    static char[][] jocVisible;
    static int casillasDestapadas = 0;
    static int casillasConBandera = 0;


    /**
     * inicialitzem els parametres i anem cridant les funcions per anar realitzant els passos del programa
     *
     * @param f numero de files
     * @param c numero de columnes
     * @param b numero de bombes
     */
    public static void iniciJoc(int f, int c, int b) {
        nFiles = f;
        nColumnes = c;
        nBombes = b;
        jocOcult = new char[f][c];
        jocVisible = new char[f][c];
        inicialitzarCamp(jocOcult, ' ');
        inicialitzarCamp(jocVisible, '.');
        posarBomba(jocOcult, nBombes);
        comptarBombes(jocOcult);
        mostraCamps();
    }




    /**
     * Aquesta funcio crea un array bidimensional de tipos char amb els caracters seleccionats a la funcio iniciJoc
     *
     * @param campMines array de chars que sera la base del joc de l'usuari / resultat
     * @param mostra els chars que la funcio utilitza per onplenar l'array.
     * @return l'array creat de tipos char
     */
    static char[][] inicialitzarCamp(char campMines[][], char mostra) {
        for (int i = 1; i < campMines.length - 1; i++) {
            for (int j = 1; j < campMines[0].length - 1; j++) {
                campMines[i][j] = mostra;
            }
        }
        return campMines;
    }






    /**
     * Genera bombes de forma random i les coloca per l'array bidimensional comprovant que sempre el numero de bombes sigui corrente i no en posi dos al mateix lloc.
     *
     * @param campMines el camp on vols colocar les bombes
     * @param nBombes   el numero de bombes desitjat
     */
    static void posarBomba(char[][] campMines, int nBombes) {
        int x, y;

        for (int i = 0; i < nBombes; i++) {
            do {
                x = (int) (Math.random() * (nFiles - 2) + 1);
                y = (int) (Math.random() * (nColumnes - 2) + 1);
            }
            while (jocOcult[x][y] == 'B');
            jocOcult[x][y] = 'B';
        }
    }







    /**
     * compta el numero de bombes que hi ha al voltant de la casella f c
     *
     * @param jocOcult el camp on es volen comptar les bombes
     */
    static void comptarBombes(char[][] jocOcult) {
        for (int f = 1; f < jocOcult.length - 1; f++) {
            for (int c = 1; c < jocOcult[0].length - 1; c++) {
                // Comptar cuantes bombes
                int bombes = 0;
                if (jocOcult[f][c] != 'B') {
                    if (jocOcult[f - 1][c - 1] == 'B') bombes++;
                    if (jocOcult[f - 1][c] == 'B') bombes++;
                    if (jocOcult[f - 1][c + 1] == 'B') bombes++;
                    if (jocOcult[f][c - 1] == 'B') bombes++;
                    if (jocOcult[f][c + 1] == 'B') bombes++;
                    if (jocOcult[f + 1][c - 1] == 'B') bombes++;
                    if (jocOcult[f + 1][c] == 'B') bombes++;
                    if (jocOcult[f + 1][c + 1] == 'B') bombes++;
                    char conversio = Character.forDigit(bombes, 10);
                    jocOcult[f][c] = conversio;
                    if (jocOcult[f][c] == '0') {
                        jocOcult[f][c] = ' ';
                    }
                    }
                }
            }
        }







    /**
     * funcio per mostrar el camp de joc a l'usuari en temps real.
     */
    public static void mostraCamps() {
        mostrarCamp(jocVisible);
    }








    /**
     * funcio que trapitja una casella triada per l'usuari i depenent de lo que hi hagi dintre de la casella realitza una accio o un altre.
     *
     * @param filesU    la fila de la casella triada
     * @param columnesU la columna de la casella triada
     */
    public static void trapitja(int filesU, int columnesU) {
        if (jocOcult[filesU][columnesU] == 'B') {
            Vista.missatgeJunt("Has trepitjat una bomba, GAME OVER!");
            jocVisible[filesU][columnesU] = 'B';
            System.out.println(' ');
            mostraSolucio();
            jocFinalitzat = true;
        } else if (jocVisible[filesU][columnesU] != '.') {
            if (jocVisible[filesU][columnesU] == 'F') {
                Vista.missatge("Aquí hi ha una bandera, si vols trapitjarla has de treure la bandera");
            } else {
                Vista.missatge("Ja has trepitjat aquesta casella anteriorment");
            }
        } else {
            trapitjaRecursivament(filesU,columnesU);
            mostraCamps();
            if (comprovarVictoria()){
                jocFinalitzat = true;
            }
        }
    }







    /**
     * Fa lo mateix que la funcio trapitja pero en comptes de trepitjarla nomes la marca amb una flag.
     *
     * @param filesU    la fila de la casella triada
     * @param columnesU la columna de la casella triada
     */
    public static void flag(int filesU, int columnesU) {
        if (jocVisible[filesU][columnesU] != '.' && jocVisible[filesU][columnesU] != 'F') {
            Vista.missatge("Ja has trepitjat aquesta casella!");
        } else if (jocVisible[filesU][columnesU] == '.') {
            jocVisible[filesU][columnesU] = 'F';
            mostraCamps();
            casillasConBandera++;
        } else if (jocVisible[filesU][columnesU] == 'F') {
            jocVisible[filesU][columnesU] = '.';
            mostraCamps();
            casillasConBandera--;
        }
        if (comprovarVictoria()){
            jocFinalitzat = true;
        }
    }








    /**
     * Aquesta funcio comprova si s'esta jugant a dintre del camp jugable.
     * @param f num de files
     * @param c num de columnes
     * @return si es troba dintre dels limits retorna true, si no false.
     */
    public static boolean verificacio(int f, int c){
        if (f < 1 || f > 8 || c < 1 || c > 8){
            return false;
        }
        else return true;
    }








    /**
     * comprova si l'usuari a guanyat contabilitzant el num de caselles destapades i les banderes posades.
     *
     * @return true si ha guanyat
     */
    public static boolean comprovarVictoria() {
        if (casillasConBandera == nBombes && casillasDestapadas == ((nFiles - 2) * (nColumnes - 2)) - nBombes){
            Vista.missatge("Has guanyat");
            return true;
        } else {
            return false;
        }
    }


    /**
     * funcio per comprovar si el joc ha acabat
     * @return retorna true si compleix la funcio
     */
    public static boolean jocAcabat(){
        return jocFinalitzat;
    }






    /**
     * mostra la solucio a l'usuari un cop ha perdut.
     */
    static void mostraSolucio() {
        mostrarCamp(jocOcult);
    }








    /**
     * aquesta funcio trepitja recursivament i va destapant les caselles del voltant sempre que no hi hagin bombes al voltant
     * i la casella trepitjada estigui buida
     * @param f files
     * @param c columnes
     */
    static void trapitjaRecursivament(int f, int c){
        if (!verificacio(f, c))
            return;
        if (jocVisible[f][c] != '.'){
            return;
        }
        jocVisible[f][c] = jocOcult[f][c];
        casillasDestapadas++;
        if (jocVisible[f][c] == ' '){
            trapitjaRecursivament(f-1, c-1);
            trapitjaRecursivament(f-1, c);
            trapitjaRecursivament(f-1, c+1);
            trapitjaRecursivament(f, c-1);
            trapitjaRecursivament(f, c+1);
            trapitjaRecursivament(f+1, c-1);
            trapitjaRecursivament(f+1, c);
            trapitjaRecursivament(f+1, c+1);
        }
    }
}
