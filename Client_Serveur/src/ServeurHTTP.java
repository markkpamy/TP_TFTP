import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Fabien on 24/05/2017.
 */
public class ServeurHTTP implements Runnable{

    private InetAddress clientAdress;
    private int portEcoute;
    private Socket clientSocket;
    private int clientPort = 0;


    public ServeurHTTP(InetAddress clientAdress, Socket clientSocket, int clientPort) {
        this.clientAdress = clientAdress;
        this.clientSocket = clientSocket;
        this.clientPort = clientPort;
    }



    @Override
    public void run() {
        /**
         * Creation du flux in et out
         */
        DataInputStream inFromClient;
        try {
            inFromClient = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outToClient;
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
            this.recevoir(inFromClient, outToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recevoir(DataInputStream infromClient, DataOutputStream outToClient)
    {
        String[] input = new String[0];
        try {
            input = infromClient.readLine().split(" ");
            System.out.println(input[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] path = input[1].split("/");
        String chemin=path[1];

        File file = new File(chemin);
        int size = (int) file.length();
        System.out.println(size);
        FileInputStream fichier;
        try {
            fichier = new FileInputStream(chemin);
            System.out.println("Fichier trouvé");
            byte buffer[] = new byte[size];

            //On fait le header reponse
            Date date = new Date();
            String lastModified = "Last-Modified"+file.lastModified();
            String contentLenght = "Content-Lenght:" +size;
            String contentType = "Content-Type:"+file.getClass().getTypeName();
            String header = date+"\n"+lastModified+"\n"+contentLenght+contentType;

            fichier.read(buffer);
            fichier.close();
            String length = "Lenght:"+size;
            String reponseClient = length;
            System.out.println("on écrit la taille");
//            outToClient.writeBytes(reponseClient);
  //          outToClient.close();
    //        outToClient.flush();
            System.out.println("On écrite le fichier");
            outToClient.write(buffer);
            outToClient.close();
            System.out.println("écriture du fichier finit");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
