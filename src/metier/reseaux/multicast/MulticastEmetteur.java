package metier.reseaux.multicast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import main.Controleur;
import metier.actions.Action;

public class MulticastEmetteur {

    private final Controleur ctrl;
    private final InetAddress ip;
    private final int port;
    private final MulticastSocket socket;

    public MulticastEmetteur(Controleur ctrl, InetAddress ip, int port) throws IOException {
        this.ctrl = ctrl;
        this.ip = ip;
        this.port = port;
        this.socket = new MulticastSocket(port);
        this.socket.joinGroup(ip);
    }

    public void envoyerAction(Action action) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(action);
        byte[] data = baos.toByteArray();
        this.socket.send(new DatagramPacket(data, data.length, this.ip, this.port));
    }

    public void deconnexion() {
        this.socket.disconnect();
    }
    
}
