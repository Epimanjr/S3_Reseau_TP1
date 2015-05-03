package reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author blaise
 */
public class ServeurUDP {

    public int port;
    String name;
    String fin;

    public ServeurUDP(int port, String name, String fin) {
        this.port = port;
        this.name = name;
        this.fin = fin;
    }

    public void ecouterInfini() {
        try {
            byte[] tbyte = new byte[250];

            DatagramPacket p = new DatagramPacket(tbyte, tbyte.length);
            DatagramSocket s = new DatagramSocket(this.port);

            while (true) {
                try {

                    s.receive(p);

                    byte[] data = p.getData();
                    String str = new String(data);
                    System.out.println("Reçu : " + str);

                    String sSend = name + ";" + str + ";" + fin;
                    byte[] dataSend = sSend.getBytes();

                    DatagramPacket p2 = new DatagramPacket(dataSend, dataSend.length, p.getAddress(), p.getPort());

                    p2.setData(dataSend);
                    s.send(p2);

                } catch (SocketException ex) {
                    Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } catch (SocketException ex) {
            Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ecouter() {
        try {
            byte[] tbyte = new byte[250];

            DatagramPacket p = new DatagramPacket(tbyte, tbyte.length);
            DatagramSocket s = new DatagramSocket(this.port);

            try {

                s.receive(p);

                byte[] data = p.getData();
                String str = new String(data);
 
                System.out.println("Reçu : "+str);
                String sSend = name + " " + str + " " + fin;
                byte[] dataSend = sSend.getBytes();
          
                p.setAddress(p.getAddress());
                p.setPort(p.getPort());

                //Envoi du message
            
                p.setData(dataSend);
                p.setLength(dataSend.length);
               
                s.send(p);

            } catch (SocketException ex) {
                Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SocketException ex) {
            Logger.getLogger(ServeurUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        ServeurUDP s = new ServeurUDP(new Integer(args[0]), args[1], args[2]);
        s.ecouter();
    }
}
