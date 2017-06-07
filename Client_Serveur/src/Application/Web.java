package Application;

import Serveur.ServeurPrimaire;

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
            for(int i=0; i<recu.length; i++)
                System.out.println(recu[i]);
            controler.setImageView(recu[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setControler(Controler controler) {
        this.controler = controler;
    }
}
