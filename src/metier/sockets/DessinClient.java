package metier.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import main.Controleur;

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
            System.out.println("En attente d'un message");
            int type = this.recevoirEntier();
            System.out.println("Type reçu: " + type);
            while (type > 0)
            {
                System.out.println("Début du traitement de la chaine");
                this.traiterChaine(type);
                type = this.recevoirEntier();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void traiterChaine(int type) throws Exception
    {
        switch(type)
        {
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
    }
    
}
