package paquet;
import carte.Carte;
import java.util.Random;

public class Paquet {
    private Carte[] cartes;
    
    public Carte[] getCartes() {
        return cartes;
    }

    public void setCartes(Carte[] cartes) {
        this.cartes = cartes;
    }

    private static final Random random = new Random();

    private int taille_pioche(){
        for(int i = 0; i<this.cartes.length;i++){
            if(this.cartes[i] == null )
                return i;
        }
        return 0;
    }

    public void melanger() {
        for (int i = this.taille_pioche()- 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            
            Carte temp = this.cartes[i];
            this.cartes[i] = this.cartes[j];
            this.cartes[j] = temp;
        }
    }



    public Carte piocher() {
        int taille =  this.taille_pioche();
        Carte carte_piochee = this.cartes[taille-1];
        cartes[taille-1] = null;
        return carte_piochee;
    }
}