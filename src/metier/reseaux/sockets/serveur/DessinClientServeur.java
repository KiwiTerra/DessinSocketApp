package metier.reseaux.sockets.serveur;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import main.Controleur;
import metier.Joueur;
import metier.actions.Action;
import metier.reseaux.sockets.Messages;

public class DessinClientServeur extends Thread {

    private final DessinServeur serveur;
    private final Socket        client;
    private final Controleur    ctrl;

    private final ObjectOutputStream output;
    private final ObjectInputStream  input;

    private String pseudo;

    public DessinClientServeur(DessinServeur serveur, Socket client) throws IOException {
        this.client = client;
        this.serveur = serveur;
        this.ctrl = serveur.getCtrl();

        this.output = new ObjectOutputStream(this.client.getOutputStream());
        this.input  = new ObjectInputStream(this.client.getInputStream());
    }

    @Override
    public void run() {
        try {
            int type = this.recevoirEntier();
            while (type > 0 && !this.client.isClosed()) {
                this.traiterChaine(type);
                type = this.recevoirEntier();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof EOFException || e instanceof SocketException) {
                try {
                    this.serveur.deconnexion(this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            this.fermer();
        }
    }

    private void traiterChaine(int type) throws IOException, ClassNotFoundException {
        System.out.println("Message reçu ! (" + type + ")");
        switch (type) {
            case Messages.AJOUTER_JOUEUR:
                String pseudo = this.recevoirChaine();
                this.envoyer(Messages.AJOUTER_JOUEUR);

                boolean pseudoValide = this.ctrl.verifierPseudo(pseudo);
                if(!pseudoValide) {
                    this.envoyer("false");
                    this.client.close();
                    return;
                }

                this.envoyer("true");
                this.pseudo = pseudo;
                break;
            case Messages.DEMANDER_ACTIONS:
                this.envoyerActions();
                this.serveur.connexion(this);
                break;

            case Messages.DESSINER:
                Action action = (Action) this.input.readObject();
                if(!action.getUtilisateur().equals(this.ctrl.getJoueur().getNom()))
                    this.ctrl.dessiner(action, false);
                this.serveur.envoyerAction(action);
                break;
            
            default:
                break;
        }
    }

    public void envoyerActions() throws IOException {
        this.envoyer(Messages.DEMANDER_ACTIONS);
        ArrayList<Action> actions = this.ctrl.getActions();
        this.output.writeObject(actions);
        this.output.writeObject(this.ctrl.getJoueurs());
    }

    public void envoyerAction(Action action) throws IOException {
        this.envoyer(Messages.DESSINER);
        this.output.writeObject(action);
    }

    public void connexion(Joueur joueur) throws IOException {
        this.envoyer(Messages.CONNEXION);
        this.output.writeObject(joueur);
    }

    public void deconnexion(String pseudo2) throws IOException {
        this.envoyer(Messages.DECONNEXION);
        this.envoyer(pseudo2);
    }

    public void envoyer(String message) throws IOException
    {
        this.output.writeUTF(message);
        this.output.flush();
    }

    private void envoyer(int message) throws IOException
    {
        this.envoyer(message + "");
    }

    public String recevoirChaine() throws IOException {
        return this.input.readUTF();
    }

    public int recevoirEntier() throws IOException {
        String chaine = this.recevoirChaine();
        System.out.println("Chaine reçue : " + chaine + " (" + chaine.length() + ")");
        if (chaine == null || chaine.isEmpty())
            return -1;

        return Integer.parseInt(chaine);
    }

    public String getPseudo() {
        return pseudo;
    }

    public void fermer() {
        try {
            this.input.close();
            this.output.close();
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
