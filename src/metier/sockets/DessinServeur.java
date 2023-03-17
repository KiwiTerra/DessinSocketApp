package metier.sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import main.Controleur;

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
}