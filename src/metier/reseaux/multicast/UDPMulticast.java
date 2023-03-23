package metier.reseaux.multicast;

import java.io.IOException;
import java.net.InetAddress;

import main.Controleur;
import metier.actions.Action;

public class UDPMulticast {

    private final MulticastRecepteur recepteur;
    private final MulticastEmetteur emetteur;

    public UDPMulticast(Controleur ctrl, InetAddress ip, int port) throws IOException {
        this.recepteur = new MulticastRecepteur(ctrl, ip, port);
        this.emetteur = new MulticastEmetteur(ctrl, ip, port);
    }

    public void demarrer() {
        this.recepteur.start();
    }

    public void deconnexion() {
        this.recepteur.deconnexion();
        this.emetteur.deconnexion();
    }

    public void envoyerAction(Action action) throws IOException {
        this.emetteur.envoyerAction(action);
    }

}
