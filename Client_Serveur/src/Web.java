import java.io.IOException;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Web {

    private Controler controler;

    ServeurPrimaire serveurPrimaire;

    Client client;

    public Web(){
        this.serveurPrimaire = new ServeurPrimaire();

    }

    public void requeteClient(String chemin, String fichier, int port){
        this.client = new Client();
        System.out.println("On créer le client");
        try {
            this.serveurPrimaire.lancer();
            client.recevoir(chemin,fichier,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
