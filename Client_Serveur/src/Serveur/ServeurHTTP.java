package Serveur;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Date;

/**
 * Created by Fabien on 24/05/2017.
 */
public class ServeurHTTP implements Runnable {

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

    public void recevoir(DataInputStream infromClient, DataOutputStream outToClient) {
        System.out.println("Dans recevoir de serveur http");
        String[] input = {};
        try {
            System.out.println("Dans la réception entête client");
            String headerRequete = "";

            headerRequete = infromClient.readLine();
            System.out.println(headerRequete);
            input = headerRequete.split(" ");

        } catch (IOException e) {
            e.printStackTrace();
            erreur504(outToClient);
        }
        ////SI L ENTETE CONTIENT GET ON CONTINUE SINON BAD GATEWAY
        if (input[0].equals("GET")) {

            String chemin = input[1].split("/")[1];

            ///ON CHARGE LE FICHIER
            File file = new File(chemin);
            int size = (int) file.length();
            FileInputStream fichier;
            try {
                ///ON ECRIT LES DONNEES DU FICHIER DANS LE BUFFER
                fichier = new FileInputStream(chemin);
                byte buffer[] = new byte[size];
                fichier.read(buffer);
                fichier.close();

                //ON FAIT LE HEADER REPONSE
                Date date = new Date();
                String lastModified = "Last-Modified" + file.lastModified();
                String contentLength = "Content-Length:" + size;
                String server = ("Server: " + this.clientSocket.getInetAddress());
                String contentType = "Content-Type:" + file.getClass().getTypeName();
                String header = "HTTP/1.1 200 OK" + "SEPARATEUR" + date + "SEPARATEUR" + lastModified + "SEPARATEUR" + server + "SEPARATEUR" + contentLength + "SEPARATEUR" + contentType + "SEPARATEUR+\n";


                System.out.println("on écrit le header");
                outToClient.writeBytes(header);
                outToClient.flush();


                System.out.println("On écrit le fichier");
                outToClient.write(buffer);
                outToClient.flush();
                outToClient.close();
                infromClient.close();
                System.out.println("écriture du fichier finit");
                clientSocket.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                try {
                    outToClient.writeBytes("HTTP/1.1 Error-404");
                    outToClient.flush();
                    outToClient.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                erreur504(outToClient);
                e.printStackTrace();
            }
        } else
            erreur504(outToClient);

    }

    private void erreur504(DataOutputStream dataOutputStream) {
        try {
            dataOutputStream.writeBytes("HTTP/1.1 Error-504");
            dataOutputStream.flush();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
