package paquet;
import carte.Carte;

public class Paquet {
    private List<Carte> cartes;
    
    public Paquet() {
        this.cartes = new ArrayList<Carte>();
    }

    public void melanger() {
        Collections.shuffle(cartes);
    }

    public Carte piocherCarte() {
        if (cartes.isEmpty()) {
            return null; // ou gérer l'erreur selon le besoin
        }
        return cartes.remove(cartes.size() - 1);
    }
}