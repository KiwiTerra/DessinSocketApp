package metier.reseaux.sockets.serveur;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;

import main.Controleur;
import metier.reseaux.multicast.UDPMulticast;

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
                DessinClientServeur client = new DessinClientServeur(this.ctrl, this.serverSocket.accept());
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

}