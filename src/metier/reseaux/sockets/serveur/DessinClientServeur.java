package metier.reseaux.sockets.serveur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import main.Controleur;
import metier.actions.Action;
import metier.reseaux.sockets.Messages;

public class DessinClientServeur extends Thread {

    private final Socket        client;
    private final Controleur    ctrl;

    private final ObjectOutputStream output;
    private final ObjectInputStream  input;

    public DessinClientServeur(Controleur ctrl, Socket client) throws IOException {
        this.client = client;
        this.ctrl = ctrl;

        this.output = new ObjectOutputStream(this.client.getOutputStream());
        this.input  = new ObjectInputStream(this.client.getInputStream());
    }

    @Override
    public void run() {
        try {
            int type = this.recevoirEntier();
            while (type > 0) {
                this.traiterChaine(type);
                type = this.recevoirEntier();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.fermer();
        }
    }

    private void traiterChaine(int type) throws IOException {
        System.out.println("Message reçu ! (" + type + ")");
        switch (type) {
            case Messages.AJOUTER_JOUEUR:
                String pseudo = this.recevoirChaine();
                this.envoyer(Messages.AJOUTER_JOUEUR);
                this.envoyer(this.ctrl.verifierPseudo(pseudo) ? "true" : "false");
                break;
            case Messages.DEMANDER_ACTIONS:
                this.envoyerActions();
                break;
            
            default:
                break;
        }
    }

    public void envoyerActions() throws IOException {
        this.envoyer(Messages.DEMANDER_ACTIONS);
        ArrayList<Action> actions = this.ctrl.getActions();
        this.output.writeObject(actions);
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
