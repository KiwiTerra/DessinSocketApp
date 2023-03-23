package metier.reseaux.multicast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import main.Controleur;
import metier.actions.Action;

public class MulticastRecepteur extends Thread {

    private static final int bufferSize = 1024 * 4;

    private final Controleur ctrl;
    private final InetAddress ip;
    private final int port;
    private final MulticastSocket socket;

    public MulticastRecepteur(Controleur ctrl, InetAddress ip, int port) throws IOException {
        this.ctrl = ctrl;
        this.ip = ip;
        this.port = port;
        this.socket = new MulticastSocket(port);
        this.socket.joinGroup(ip);
    }

    @Override
    public void run() {
        while(true) {
            byte[] buffer = new byte[bufferSize];
            try {
                this.socket.receive(new DatagramPacket(buffer, bufferSize, ip, port));
                System.out.println("Objet reçu !");

                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ObjectInputStream ois = new ObjectInputStream(bais);

                Object object = ois.readObject();
                if(object instanceof Action) {
                    System.out.println("Action reçue !");
                    this.ctrl.dessiner((Action) object, false);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void deconnexion() {
        this.socket.disconnect();
        this.interrupt();
    }
    
}
