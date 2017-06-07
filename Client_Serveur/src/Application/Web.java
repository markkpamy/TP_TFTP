package Application;

import Serveur.ServeurPrimaire;

import java.io.File;
import java.io.IOException;

/**
 * Created by Fabien on 31/05/2017.
 */
public class Web {

    private Controler controler;

    ServeurPrimaire serveurPrimaire;

    Client client;

    public Web(){
        //this.serveurPrimaire = new ServeurPrimaire();
    }

    public void requeteClient(String chemin, String fichier, int port){
        System.out.println("On créer le client");
        this.client = new Client();
        try {
            //this.serveurPrimaire.lancer();
            String[] recu = client.recevoir(chemin,fichier,port);
            System.out.println("fichier recu :"+recu[0]);
            File file =
            while(recu[0].isEmpty()){}
            controler.setImageView(recu[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
