package metier.reseaux.sockets.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import main.Controleur;
import metier.Joueur;
import metier.actions.Action;

public class DessinServeur extends Thread {

    private final Controleur ctrl;
    private ServerSocket serverSocket;

    private final ArrayList<DessinClientServeur> clients;

    public DessinServeur(Controleur ctrl) {
        this.ctrl = ctrl;
        this.clients = new ArrayList<>();
    }
    
    public void demarrerServeur() throws IOException {
        this.serverSocket = new ServerSocket(3000);
        System.out.println("Serveur démarré sur le port 3000");
    }

    public void arreterServeur() {
        if(this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(DessinClientServeur client : this.clients) {
            client.interrupt();
        }

        this.interrupt();
        this.clients.clear();
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

        dessinClientServeur.interrupt();

        if(dessinClientServeur.getPseudo() == null)
            return;

        for(DessinClientServeur client : this.clients) {
            client.deconnexion(pseudo);
        }

        this.ctrl.retirerJoueur(pseudo);
    }

    public void envoyerAction(Action action) throws IOException {
        for(DessinClientServeur client : this.clients) {
            client.envoyerAction(action);
        }
    }

    public void supprimerAction(Action action) throws IOException {
        for(DessinClientServeur client : this.clients) {
            client.supprimerAction(action);
        }
    }
}