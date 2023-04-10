package minesMJB.controlador;

import java.util.Scanner;

import minesMJB.model.Model;
import minesMJB.vista.Vista;

/**
 * En aquesta classe l'usuari es on interactua amb el programa introduint les dades per anar jugant.
 */
public class Controlador {

    static Scanner scan = new Scanner(System.in);

    /**
     * Aqui l'usuari ha d'anar jugant, triant les opcions que vol fer, sigui trepitjar una casella, posar una flag o acabar la partida.
     */
    public static void jugar() {
        char opcioUsuari;
        char filesUsuariTemp = '0';
        int filesUsuari = 0;
        int columnesUsuari = 0;
        boolean jocAcabat = false;
        Model.iniciJoc(10, 10, 10);
        do {
            do {
                Vista.missatgeJunt("T = Trapitjar || F = Flag || A = Acabar: ");
                opcioUsuari = scan.next().charAt(0);
                opcioUsuari = Character.toUpperCase(opcioUsuari);
                scan.nextLine();
                if (opcioUsuari == 'A') {
                    Vista.missatge("Fins un altre!");
                    return;
                }
                if (opcioUsuari != 'T' && opcioUsuari != 'F') {
                    Vista.missatge("Has d'introduir una opcio valida");
                }
            } while (opcioUsuari != 'T' && opcioUsuari != 'F');
            if (opcioUsuari == 'T') {
                do {
                    Vista.missatge("Introdueix la fila que vulguis destapar i la columna (separades per un espai): ");
                    filesUsuariTemp = scan.next().charAt(0);
                    filesUsuariTemp = Character.toUpperCase(filesUsuariTemp);
                    columnesUsuari = scan.nextInt();
                    scan.nextLine();
                    if ((!(filesUsuariTemp >= 'A' && filesUsuariTemp <= 'H' && columnesUsuari >= 1 && columnesUsuari <= 8))) {
                        Vista.missatge("Error, has d'introduir una fila entre 'A' i 'H' o una columna entre 1 i 8");
                    }
                    filesUsuari = filesUsuariTemp - 'A' + 1;
                    Model.trapitja(filesUsuari, columnesUsuari);
                    if (Model.jocAcabat()) {
                        return;
                    }

                } while (!(filesUsuariTemp >= 'A' && filesUsuariTemp <= 'H' && columnesUsuari >= 1 && columnesUsuari <= 8));
            }
            if (opcioUsuari == 'F') {
                do {
                    Vista.missatgeJunt("Introdueix la fila que vulguis posar la bandera i la columna (separades per un espai): ");
                    filesUsuariTemp = scan.next().charAt(0);
                    filesUsuariTemp = Character.toUpperCase(filesUsuariTemp);
                    columnesUsuari = scan.nextInt();
                    scan.nextLine();

                    if ((!(filesUsuariTemp >= 'A' && filesUsuariTemp <= 'H' && columnesUsuari >= 1 && columnesUsuari <= 8))) {
                        Vista.missatge("Error, has d'introduir una fila entre 'A' i 'H' o una columna entre 1 i 8");
                    }
                    filesUsuari = filesUsuariTemp - 'A' + 1;
                    Model.flag(filesUsuari, columnesUsuari);
                    if (Model.jocAcabat()) {
                        return;
                    }
                } while (!(filesUsuariTemp >= 'A' && filesUsuariTemp <= 'H' && columnesUsuari >= 1 && columnesUsuari <= 8));
            }
        } while (!(Model.comprovarVictoria()));
    }
}
