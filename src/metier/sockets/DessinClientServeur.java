package metier.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.Controleur;

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

    private void traiterChaine(int type) throws IOException {
        System.out.println("Message reçu ! (" + type + ")");
        switch (type) {
            default:
                break;
        }
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
