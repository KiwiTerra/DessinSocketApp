package metier.reseaux.sockets.serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import main.Controleur;
import metier.Joueur;
import metier.reseaux.multicast.UDPMulticast;
import metier.reseaux.sockets.Messages;

public class DessinServeur extends Thread {

    private final Controleur ctrl;
    private ServerSocket serverSocket;
    private UDPMulticast multicast;

    private final ArrayList<DessinClientServeur> clients;

    public DessinServeur(Controleur ctrl) {
        this.ctrl = ctrl;
        this.clients = new ArrayList<>();
    }
    
    public void demarrerServeur() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        System.out.println("Serveur démarré sur le port 3000");
        this.multicast = new UDPMulticast(this.ctrl, InetAddress.getByName("239.255.80.84"), 8084);
        this.multicast.demarrer();
        System.out.println("Multicast démarré");
    }

    @Override
    public void run() {
        while (!this.serverSocket.isClosed()) {
            try {
                DessinClientServeur client = new DessinClientServeur(this, this.serverSocket.accept());
                this.clients.add(client);
                System.out.println("Nouveau client connecté");
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public UDPMulticast getMulticast() {
        return multicast;
    }

    public Controleur getCtrl() {
        return ctrl;
    }

    public void connexion(DessinClientServeur dessinClientServeur) throws IOException {
        String pseudo = dessinClientServeur.getPseudo();
        if(pseudo == null)
            return;

        Joueur joueur = this.ctrl.ajouterJoueur(pseudo);
        for(DessinClientServeur client : this.clients) {
            client.connexion(joueur);
        }
    }

    public void deconnexion(DessinClientServeur dessinClientServeur) throws IOException {
        String pseudo = dessinClientServeur.getPseudo();
        this.clients.remove(dessinClientServeur);

        if(dessinClientServeur.getPseudo() == null)
            return;

        for(DessinClientServeur client : this.clients) {
            client.deconnexion(pseudo);
        }

        this.ctrl.retirerJoueur(pseudo);
    }
}