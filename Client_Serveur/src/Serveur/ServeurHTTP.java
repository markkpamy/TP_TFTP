package Serveur;

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
        System.out.println("Dans le run de sHTTP");
        /**
         * Creation du flux in et out
         */
        DataInputStream inFromClient;
        DataOutputStream outToClient;
        //Dans le run de serveur
        try {
            inFromClient = new DataInputStream(clientSocket.getInputStream());
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
            this.recevoir(inFromClient, outToClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recevoir(DataInputStream infromClient, DataOutputStream outToClient)
    {
        System.out.println("Dans recevoir de serveur http");
        String[] input = new String[0];
        try {
            System.out.println("Dans la réception entête client");
            String headerRequete ="";

            headerRequete = infromClient.readLine();
            //outToClient.flush();
            //infromClient.close();
            System.out.println(headerRequete);
            input = headerRequete.split(" ");
            System.out.println(input[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String chemin=input[1].split("/")[1];

        File file = new File(chemin);
        int size = (int) file.length();
        System.out.println(size);
        FileInputStream fichier;
        try {
            fichier = new FileInputStream(chemin);
            System.out.println("Fichier trouvé");
            byte buffer[] = new byte[size];
            fichier.read(buffer);
            fichier.close();
            System.out.println("Ecriture dans le buffer effectué");
            //On fait le header reponse
            Date date = new Date();
            String lastModified = "Last-Modified"+file.lastModified();
            String contentLength = "Content-Length:" +size;
            String contentType = "Content-Type:"+file.getClass().getTypeName();
            String header = "HTTP/1.1 200 OK"+"SEPARATEUR"+date+"SEPARATEUR"+lastModified+"SEPARATEUR"+contentLength+"SEPARATEUR"+contentType+"SEPARATEUR+\n";


            System.out.println("on écrit le header");
            outToClient.writeBytes(header);
            //outToClient.close();
            outToClient.flush();


            System.out.println("On écrite le fichier");
            //outToClient = new DataOutputStream(clientSocket.getOutputStream());
            outToClient.write(buffer);
            outToClient.flush();
            boolean problem = false;
            outToClient.close();
            System.out.println("écriture du fichier finit");
            //clientSocket.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
