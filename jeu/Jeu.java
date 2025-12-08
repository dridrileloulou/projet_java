package joueur;
import plateau.Plateau;
import joueur.Joueur;

public class Jeu{
    private Plateau plateau;
    private Joueur[] joueurs;
    private int TourActuel;

    public Jeu(){
        this.plateau = new Plateau();
        this.joueurs = new Joueur[2];
        for (int i=0; i<2; i++){
            joueurs[i] = new Joueur("Joueur " + (i+1), i);
        }
        this.TourActuel = 0;
    }
}