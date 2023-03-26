package metier.reseaux.sockets;

public class Messages {

    // Demander à rejoindre une partie
    public static final int AJOUTER_JOUEUR          = 1;

    // Demander les actions déjà effectuées
    public static final int DEMANDER_ACTIONS        = 2;

    // Informer d'une connexion
    public static final int CONNEXION               = 3;

    // Informer d'une déconnexion
    public static final int DECONNEXION             = 4;

    // Informer d'un dessin
    public static final int DESSINER                = 5;

    // Informer d'une suppression de dessin
    public static final int SUPPRIMER_DESSIN        = 6;

}
