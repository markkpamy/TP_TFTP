import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Fabien on 24/05/2017.
 */
public class Client {

    private Socket clientSocket;
    private InetAddress adresseIp;
    private String fichier;
    private String requete;

    public Client() {
        try {
            adresseIp = java.net.InetAddress.getByName("localhost");
            clientSocket = new Socket(adresseIp, 2026);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recevoir(String adresse, String fichier, int port) throws IOException {
        System.out.println("Dans réception");
        DataInputStream inFromServer;
        DataOutputStream outToServer;

        //Ouverture des flux
        inFromServer = new DataInputStream(clientSocket.getInputStream());
        outToServer = new DataOutputStream(clientSocket.getOutputStream());

        //On fait le header requete
        String requete = "GET "+adresse+"/"+fichier+" HTTP/1.1";

        outToServer.writeBytes(requete);
        outToServer.close();
        //Determination de l'header
        //POUR LINSTANT JUSTE LONGUEUR
        System.out.println("On recherche l'information");
//        String header = inFromServer.readLine();
        String header ="";
//        inFromServer.close();
        //inFromServer = new DataInputStream(clientSocket.getInputStream());
        System.out.println("information passée");
        int longueur = 0;

        if(header.contains("Lenght"))
            longueur = Integer.parseInt(header.split(":")[1]);
        System.out.println(header);

        //Creation du table de byte pour la reception
        byte data[] = new byte[longueur];

        //Reception du fichier
        for (int i = 0; i < data.length; i++) {
            data[i] = inFromServer.readByte();
        }
        inFromServer.close();

        //Creation du fichier
        String nomFichier="image_Recu_";
        nomFichier +=fichier;
        File file = new File(nomFichier);
        file.createNewFile();

        //Ecriture du contenu du fichier
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.close();
        clientSocket.close();
    }
}
