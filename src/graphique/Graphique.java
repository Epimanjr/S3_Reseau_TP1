/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphique;

import reseau.ClientGraphique;
import reseau.ServeurUDP;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author blaise
 */
public class Graphique extends JFrame {

    JPanel panHaut = new JPanel();
    JPanel panCenter = new JPanel();
    JButton client = new JButton("client");
    JTextField saisie = new JTextField(15);
    JTextField saisieIp = new JTextField(10);
    JTextField saisiePort = new JTextField(5);

    JButton bok = new JButton("Envoyer");
    JLabel resClient = new JLabel("En attente");
    JLabel recu = new JLabel("En attente de reception...");
    JLabel labelServeur = new JLabel("<html><p>Serveur</p>");
    
    String ip;
    String port;
    String message;

    public Graphique(String ip, String port, String message) {
        super();
        
        this.ip = ip;
        this.port = port;
        this.message = message;

        setListeners();
        setWindow();
    }

    public void setListeners() {

        panCenter = new JPanel();
        panCenter.setLayout(new BorderLayout());

        JPanel panCenter2 = new JPanel();
        panCenter2.setLayout(new GridLayout(4, 2));

        panCenter2.add(new JLabel("Saisie IP : "));
        panCenter2.add(new JLabel("Saisie Port : "));
        saisieIp.setText(ip);
        panCenter2.add(saisieIp);
        saisiePort.setText(port);
        panCenter2.add(saisiePort);

        panCenter2.add(new JLabel(" MON message : "));
        panCenter2.add(new JLabel(" ... "));
        saisie.setText(message);
        panCenter2.add(saisie);
        panCenter2.add(bok);

        panCenter.add(panCenter2, BorderLayout.NORTH);
        panCenter.add(resClient, BorderLayout.CENTER);
        panCenter.add(recu, BorderLayout.SOUTH);

        setPanel();

        bok.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae
                    ) {
                        ClientGraphique c = new ClientGraphique(saisie.getText());
                        String srecu = c.send(saisieIp.getText(), new Integer(saisiePort.getText()));

                        resClient.setText("<html><p style=\"color: red;\">Message envoyé</p></html>");
                        recu.setText("<html><p style=\"color: red;\">"+ srecu + "</p></html>");
                    }
                }
        );
    }

    private void setWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Client/Serveur");
        this.setPreferredSize(new Dimension(400, 400));
        this.setLayout(new BorderLayout());

        setPanel();

        this.pack();
        this.setVisible(true);
    }

    public void setPanel() {
        this.getContentPane().removeAll();

        JPanel pan = new JPanel();
        panHaut.add(client);
        pan.add(panHaut, BorderLayout.NORTH);
        pan.add(panCenter, BorderLayout.CENTER);

        this.getContentPane().add(pan);
        pack();
    }
}
