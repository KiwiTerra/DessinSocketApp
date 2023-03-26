package metier.reseaux.sockets.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import main.Controleur;
import metier.Joueur;
import metier.actions.Action;
import metier.reseaux.sockets.Messages;

public class DessinClient extends Thread {

    private final Controleur ctrl;
    private final String ip;
    private Socket socket;

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public DessinClient(Controleur ctrl, String ip)
    {
        this.ctrl = ctrl;
        this.ip = ip;
    }

    @Override
    public void run()
    {
        try
        {   
            while (true)
            {
                int type = this.recevoirEntier();
                System.out.println("Type reçu: " + type);

                System.out.println("Début du traitement de la chaine");
                this.traiterChaine(type);
                System.out.println("Fin du traitement de la chaine");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            if(e instanceof IOException)
            {
                this.ctrl.menuPrincipal();
            }
        }
    }

    private void traiterChaine(int type) throws Exception
    {
        switch(type)
        {
            case Messages.AJOUTER_JOUEUR:
                String valide = this.lire();
                boolean pseudoValide = Boolean.parseBoolean(valide);
                if(!pseudoValide)
                {
                    JOptionPane.showMessageDialog(null, "Ce pseudo est déjà utilisé par un joueur !", "Erreur", JOptionPane.ERROR_MESSAGE);
                    this.deconnexion();
                    return;
                }

                this.envoyer(Messages.DEMANDER_ACTIONS);
                break;
            case Messages.DEMANDER_ACTIONS:
                Object actions = this.input.readObject();
                this.ctrl.setActions((ArrayList<Action>) actions);

                ArrayList<Joueur> pseudos = (ArrayList<Joueur>) this.input.readObject();
                for(Joueur j : pseudos)
                    this.ctrl.ajouterJoueur(j);
                
                this.ctrl.afficherFenetreDessin();
                break;

            case Messages.CONNEXION:
                Joueur joueur = (Joueur) this.input.readObject();
                this.ctrl.ajouterJoueur(joueur);
                break;

            case Messages.DECONNEXION:
                String pseudo = this.lire();
                this.ctrl.retirerJoueur(pseudo);
                break;

            case Messages.DESSINER:
                Action action = (Action) this.input.readObject();
                if(action.getUtilisateur().equals(this.ctrl.getJoueur().getNom()))
                    break;

                this.ctrl.dessiner(action, false);
                break;

            case Messages.SUPPRIMER_DESSIN:
                action = (Action) this.input.readObject();
                if(action.getUtilisateur().equals(this.ctrl.getJoueur().getNom()))
                    break;

                this.ctrl.supprimerAction(action, false);
                break;

            default:
                break;
        }
    }

    public int recevoirEntier() throws IOException
    {
        String chaine = this.lire();
        return Integer.parseInt(chaine);
    }

    
    public String lire() throws IOException
    {
        return this.input.readUTF();
    }

    public void envoyer(int message) throws IOException
    {
        this.envoyer(message + "");
    }

    public void envoyer(String message) throws IOException
    {
        this.output.writeUTF(message);
        this.output.flush();
    }

    public void connexion() throws UnknownHostException, IOException
    {
        this.socket = new Socket(this.ip, 3000);
        this.input = new ObjectInputStream(this.socket.getInputStream());
        this.output = new ObjectOutputStream(this.socket.getOutputStream());
        
        System.out.println("Multicast démarré");
    }

    public void deconnexion()
    {
        if(!this.socket.isClosed())
            try {
                this.socket.close();
            } catch (IOException e) {}
    }

    public void envoyerPseudo(String nom) throws IOException {
        this.envoyer(Messages.AJOUTER_JOUEUR);
        this.envoyer(nom);
    }

    public void envoyerAction(Action action) throws IOException {
        this.envoyer(Messages.DESSINER);
        this.output.writeObject(action);
    }

    public void supprimerAction(Action action) throws IOException {
        this.envoyer(Messages.SUPPRIMER_DESSIN);
        this.output.writeObject(action);
    }
    
}
