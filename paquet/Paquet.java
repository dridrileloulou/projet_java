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

    private int taille_pioche(Carte[] cartes){
        for(int i = 0; i<cartes.length;i++){
            if(cartes[i] == null )
                return i+1;
        }
        return 0;
    }

    public void melanger(Carte[] cartes) {
        for (int i = taille_pioche(cartes)- 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            
            Carte temp = cartes[i];
            cartes[i] = cartes[j];
            cartes[j] = temp;
        }
    }



    public Carte piocher(Carte[] cartes) {
        int taille =  taille_pioche(cartes);
        Carte carte_piochee = cartes[taille-1];
        cartes[taille] = null;
        return carte_piochee;
    }
}