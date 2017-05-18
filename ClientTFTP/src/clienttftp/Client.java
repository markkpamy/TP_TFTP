/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttftp;
import java.io.File;
import java.net.DatagramPacket;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author markk
 */
public class Client {

private byte sendBuffer[];
private byte receiveBuffer[];
private byte[] donneesFichier;
private int serverPort = 69;
private InetAddress serverAddressIp;
private InetAddress clientAdress;
private DatagramSocket portCom;
private ClientController controller;

public Client(String fileName,ClientController controller){
    try {
    	this.controller=controller;
        this.clientAdress = InetAddress.getByName("localhost");
    } catch (UnknownHostException e) {
        e.printStackTrace();
    }
}
public Client(ClientController controller){
    this("localhost",controller);
}

public int receiveFile(String chemin, String filename,  String serverAddress, int portServer) throws Exception{

    DatagramPacket rrq;
		DatagramPacket donnees;
		DatagramPacket ack;
		FileOutputStream fichier;
		boolean echangeFini = false;

		try {
			//Ouveture socket sur un port libre

			portCom = new DatagramSocket();
			serverAddressIp = InetAddress.getByName(serverAddress);
			serverPort=portServer;
                        System.out.println("");

			//Envoi du RRQ
			sendBuffer = RRQWRQ(filename, 1);
			rrq = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddressIp, portServer);
			portCom.send(rrq);

			//Création du fichier
			File file = new File(chemin);
			if (file.exists())
            { //Si le fichier existe déjà 
				//vue.getTxtInfoArea().append("Erreur -4 : Le fichier "+(p_path+p_nomLocal)+" existe déjà .\n");
                System.out.println("Erreur -4 : Le fichier "+(chemin+filename)+" existe déjà .\n");
                return -4;
            }

			//Ouverture du flux
			fichier = new FileOutputStream(chemin);
                        System.out.println("Création du fichier réussi\\n");
                        controller.getCodeRetour().setText("Création du fichier réussi\n");
			//vue.getTxtInfoArea().append("Création du fichier réussi\n");
			//vue.repaint();

			//Tant que le serveur envoi des données
			while (!echangeFini)
			{
				//Réception du paquet
				receiveBuffer = new byte[516];
				donnees = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				portCom.receive(donnees);
				portServer = donnees.getPort();
				//Si le paquet contient bien des données
				if (receiveBuffer[1] == 3)
				{
					//Construction et envoi de l'ACK
					for (int k = 4; k < receiveBuffer.length; k++) {
						fichier.write(receiveBuffer[k]);
					}

					sendBuffer = new byte[516];
					sendBuffer[0] = 0;
					sendBuffer[1] = (byte) 4;
					sendBuffer[2] = receiveBuffer[2];
					sendBuffer[3] = receiveBuffer[3];
					ack = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddressIp, portServer);
					portCom.send(ack);

					echangeFini = donnees.getLength() < 512;
				}
				else if (receiveBuffer[1] == 5)
				{ //Sinon on retourne l'erreur que nous envoi le serveur
                                    System.out.println("");
					//vue.getTxtInfoArea().append(
					//Commun.TypeErreurServeur.getStringFromValue(receiveBuffer[3]).libelle
					//+"\n");
					return receiveBuffer[3];
				}
			}
			System.out.println("");
			//vue.getTxtInfoArea().append("Fichier recu\n");
			fichier.close();

		} catch (UnknownHostException e) {
                    System.out.println("");
			//vue.getTxtInfoArea().append("Erreur -1 : IP indéterminée\n");
			return -1;
		} catch (SocketException e) {
                    System.out.println("");
			//vue.getTxtInfoArea().append("Erreur -2 : Problème de création ou d'accès au socket\n");
			return -2;
		} catch (IOException e) {
                    System.out.println("");
			//vue.getTxtInfoArea().append("Erreur -3 : Problème réseau\n");
			return -3;
		} catch (StackOverflowError e) {
                    System.out.println("");
			//vue.getTxtInfoArea().append("Erreur -5 : Espace disque insuffisant\n");
			return -5;
		} catch (Exception e) {
                    System.out.println("");
			//vue.getTxtInfoArea().append("Erreur -6 : Problème inconnu\n");
			return -6;
		} finally {
			portCom.close();
		}
		return 0;

}


public int sendFile(String chemin, String filename, String remoteName, String serverAddress, int portServer){
		DatagramPacket WRQ;
		DatagramPacket ACK;
		FileInputStream fichier = null;
		int i = 0, j = 0, ttl = 0;

		try {
			//Ouverture socket
			serverAddressIp = InetAddress.getByName(serverAddress);
			portCom = new DatagramSocket();

			//Envoi du WRQ
			sendBuffer = RRQWRQ(remoteName, 2);
			WRQ = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddressIp, portServer);
			portCom.send(WRQ);

			//Reception ACK
			receiveBuffer = new byte[4];
			ACK = new DatagramPacket(receiveBuffer, receiveBuffer.length);
			portCom.receive(ACK);

			//Si c'est un ACK, on peut envoyer le fichier
			if(receiveBuffer[1] == 4)
			{
				serverPort = ACK.getPort();
				//vue.getTxtInfoArea().append("Serveur - "+serverAddress+":"+serverPort+"\n");
				//vue.repaint();
                                System.out.println("Serveur - "+serverAddress+":"+serverPort+"\n");
				sendBuffer = new byte[516];
				donneesFichier = new byte[512];

				//Ouverture du fichier
				try{
					fichier = new FileInputStream(chemin);
                                        System.out.println("Ouverture du fichier réussi\n");
                    controller.getCodeRetour().setText("Ouverture du fichier réussi\n");

				}
				catch (FileNotFoundException e) //Fichier non trouvé ou accès refusé.
				{
				    controller.getCodeRetour().setText("Echec de l'ouverture du fichier\n");
                                        System.out.println("Erreur -1 : Echec de l'ouverture du fichier\n");
					return -1;
				}

				//Tant que le read n'a pas lu tout le fichier
				while(fichier.read(donneesFichier)>0)
				{
					sendBuffer[0] = (byte) 0;
					sendBuffer[1] = (byte) 3;
					sendBuffer[2] = (byte) j;
					sendBuffer[3] = (byte) (i+1);

					//Copie du buffer fichier après l'entete de 4 bytes
					for (int k=0;k<(donneesFichier.length);k++){
						sendBuffer[k+4] = donneesFichier[k];
					}

					//Tant que l'ACK n'est pas le bon en envoi le paquet
					// ou que ce n'est pas un ACK
					int verifAck = -1;
					DatagramPacket donnees = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddressIp, serverPort);
					while(verifAck != (i+1)
					|| receiveBuffer[1] != 4) {
						//Envoi du paquet
						portCom.send(donnees);
						receiveBuffer = new byte[516];
						//Réception ACK
						ACK = new DatagramPacket(receiveBuffer, receiveBuffer.length);
						portCom.receive(ACK);
						verifAck = receiveBuffer[3];
						if(verifAck < 0)
							verifAck = 256 - Math.abs(receiveBuffer[3]);

						ttl++;
						if (ttl > 30) {
						    controller.getCodeRetour().setText("Délai d'attente dépassé");
						    System.out.println("Erreur -2 : Dépassement de délai\n");
							return -2;
						}
					}

					ttl = 0;
					i++;
					if (i == 255) {
						j++;
						i = -1;
						//Laisser -1 absolument sinon les paquets
						//multiple de 256 ne sont pas envoyés
					}
					sendBuffer = new byte[516];
					donneesFichier = new byte[512];
				}

				//Envoi d'un dernier paquet vide pour les fichier multiple de 512
				sendBuffer = new byte[4];
				sendBuffer[0] = (byte) 0;
				sendBuffer[1] = (byte) 3;
				sendBuffer[2] = (byte) j;
				sendBuffer[3] = (byte) (i+1);
				DatagramPacket dernierPaquet = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddressIp, serverPort);
				portCom.send(dernierPaquet);
			}
			else if (receiveBuffer[1] == 5)
			{

				return receiveBuffer[3];
			}
            controller.getCodeRetour().setText("Fichier envoyé avec succès\\n");
                        System.out.println("Fichier envoyé\n");
			fichier.close();
		}
		catch (UnknownHostException e) {
            controller.getCodeRetour().setText("Erreur -3 : IP indéterminée\n");
                        System.out.println("Erreur -3 : IP indéterminée\n");
			return -3;
		}
		catch (SocketException e1) {
                    System.out.println("Erreur -4 : Problème de création ou d'accès au socket\n");
            controller.getCodeRetour().setText("Erreur -4 : Problème de création ou d'accès au socket\n");
			return -4;
		}
		catch (IOException e1) {
                    System.out.println("Erreur -5 : Problème réseau\n");
            controller.getCodeRetour().setText("Erreur -5 : Problème réseau\n");
			return -5;
		}
		catch (Exception e) {
                    System.out.println("Erreur -6 : Problème inconnu\n");
            controller.getCodeRetour().setText("Erreur -6 : Problème inconnu\n");
			return -6;
		}finally {
			portCom.close();
		}
		return 0;

}

public byte[] RRQWRQ(String fileName,int typePaquet){
                byte[] buffer = new byte[516];
		byte opCode[] = new byte[] {0, (byte)typePaquet};
		byte fichier[] = fileName.getBytes();
		byte octetBourrage[] = new byte[] {0};
		byte mode[] = "octet".getBytes();		
		int index = 0;
		System.arraycopy(opCode, 0, buffer, index, 2);
		index += 2;
		System.arraycopy(fichier, 0, buffer, index, fichier.length);
		index += fichier.length;
		System.arraycopy(octetBourrage, 0, buffer, index, 1);
		index++;
		System.arraycopy(mode, 0, buffer, index, mode.length);
		index += mode.length;
		System.arraycopy(octetBourrage, 0, buffer, index, 1);
		return buffer;

}

}
