/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * @author markk
 */
public class Client {
    
private DatagramSocket socket;
private byte sentBuffer[];
private byte receiveBuffer[];	
private byte[] donneesFichier;
private int serverPort = 69;
private InetAddress serverAddress;
private InetAddress clientAdress;
private DatagramSocket portCom;
    
public Client(String clientAdress){
    try {
        this.clientAdress = InetAddress.getByName(clientAdress);
    } catch (UnknownHostException e) {
        e.printStackTrace();
    }
}
public Client(){
    this("localhost");
}

public int receiveFile(String addr,int port, String nomFichierDistant,String nomFichierLocal) throws Exception{
    
    DatagramPacket RRQ;
    DatagramPacket DATA;
    DatagramPacket ACK;
    FileOutputStream file;
    
    portCom = new DatagramSocket(port);
    serverAddress = InetAddress.getByName(addr);
    return 0;

}

public int sendFile(InetAddress addr,int port,String nomFichierLocal){
    String s="";
    byte[] data = new byte[1024];
    Path path = Paths.get(nomFichierLocal,s);
    try {
        data = Files.readAllBytes(path);
    } catch (IOException e) {
        e.printStackTrace();
    }
    DatagramPacket dp = new DatagramPacket(data,data.length, serverAddress, serverPort);
    try {
        portCom.send(dp);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return 0;

}

public byte[] paquetWRQ(String fileName){
                byte[] buffer = new byte[516];
		byte opCode[] = new byte[] {0, (byte)2};
		byte fichier[] = fileName.getBytes();
		byte bourrage[] = new byte[] {0};
		byte type[] = "octet".getBytes();
		
		int index = 0;
		System.arraycopy(opCode, 0, buffer, index, 2);
		index += 2;
		System.arraycopy(fichier, 0, buffer, index, fichier.length);
		index += fichier.length;
		System.arraycopy(bourrage, 0, buffer, index, 1);
		index++;
		System.arraycopy(type, 0, buffer, index, type.length);
		index += type.length;
		System.arraycopy(bourrage, 0, buffer, index, 1);
		return buffer;

}
public byte[] paquetRRQ(){
    return null;
}


}
