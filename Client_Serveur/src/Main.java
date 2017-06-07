import Application.App;
import Serveur.ServeurPrimaire;

/**
 * Created by Fabien on 07/06/2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Lancement application");
        App.main(args);
        System.out.println("Lancement serveur");
        ServeurPrimaire.main(args);
    }
}
