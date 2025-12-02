package paquet;
import carte.Carte;

public class Paquet {
    private Carte[] cartes;
    
    public Paquet() {
        this.cartes = new Cartes[];
    }

    public void melanger() {
        //à remplir
    }

    public Carte piocher() {
        return cartes[cartes.length - 1];
    }
}